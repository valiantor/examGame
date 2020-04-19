package com.valiantor.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.valiantor.entity.Question;
import com.valiantor.entity.extro.AnswerQuestion;
import com.valiantor.entity.extro.LevelQuestionInfo;
import com.valiantor.service.QuestionService;
import com.valiantor.tools.QuestionTools;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
@RequestMapping("question")
public class QuestionController {
    public static final Logger logger= LoggerFactory.getLogger(QuestionController.class);
    @Autowired
    QuestionService questionService;


    @RequestMapping("addQuestion")
    public boolean addQuestion(@RequestParam("question") String questionJson){
        Question question = new Gson().fromJson(questionJson, Question.class);
        if(question==null) return false;

        return questionService.addQuestion(question);
    }

    @RequestMapping("addQuestionListFormDb")
    public boolean addQuestionListFormDb(@RequestParam("questionList") String questionListJson){
        List<Question> questionList= new Gson().fromJson(questionListJson,
                new TypeToken<List<Question>>() {
        }.getType());
        if(CollectionUtils.isEmpty(questionList)) return false;

        return questionService.addQuestionListFormDb(questionList);
    }


    @RequestMapping("updateQuestion")
    public boolean updateQuestion(@RequestParam("question") String questionJson){
        Question question = new Gson().fromJson(questionJson, Question.class);
        if(question==null) return false;

        return questionService.updateQuestion(question);
    }


    /**
     * 查询指定关卡下面的所有题
     * @param lNo
     * @return
     */
    @RequestMapping("findAllQuestionByLNo")
    public List<Question> findAllQuestionByLNo(@RequestParam("lNo") int lNo){
        return questionService.findAllQuestionBylNo(lNo);
    }

    /**
     * 获取数据库中未在关卡里的题
     * @param currentPage 当前分页
     * @param numPerPage 每页获取的题数目
     * @return
     */

    @RequestMapping("findQuestionFromDB")
    public List<Question> findQuestionFromDB(@RequestParam("currentPage") int currentPage,@RequestParam("numPerPage") int numPerPage){
        return questionService.findQuestionFromDB(currentPage,numPerPage);
    }



    /**
     * 查询当前关卡下面指定分页的题
     * @param lNo
     * @param currentPage
     * @param numPerPage
     * @return
     */
    @RequestMapping(value = "findQuestionPageByLNo")
    public List<Question> finduestionPageByLNo(@RequestParam("lNo") int lNo,@RequestParam("currentPage") int currentPage,@RequestParam("numPerPage") int numPerPage){

        return questionService.findQuestionPageBylNo(lNo,currentPage,numPerPage);
    }
    @RequestMapping("deleteQuestionFromLevel")
    public Boolean deleteQuestionFromLevel(@RequestParam("qNo") int qNo){

        return questionService.deleteQuestionFromLevel(qNo);
    }

    @RequestMapping(value = "findRandomQuestionByLNoAndNum")
    public LevelQuestionInfo findRandomQuestionByLNoAndNum(@RequestParam("lNo") int lNo, @RequestParam("num") int num, HttpSession session){

        Object uIdObj = session.getAttribute("uId");
        String uId = null;
        if(uIdObj != null) {
            uId = String.valueOf(uIdObj);
        }
        return questionService.findRandomQuestionByLNoAndNum(lNo,num,uId);
    }


    @RequestMapping("answerQuestionList")
    public boolean answerQuestionList(@RequestParam("answerQuestionList") String answerQuestion){

        List<AnswerQuestion> answerQuestionList= new Gson().fromJson(answerQuestion, new TypeToken<List<AnswerQuestion>>() {
        }.getType());

        if(CollectionUtils.isEmpty(answerQuestionList)) return false;


        return questionService.answerQuestionList(answerQuestionList);
    }

    @RequestMapping("answerQuestion")
    public boolean answerQuestion(@RequestParam("answerQuestion") String answerQuestionJson){

        AnswerQuestion answerQuestion = new Gson().fromJson(answerQuestionJson, AnswerQuestion.class);

        if(answerQuestion == null) return false;


        return questionService.answerQuestion(answerQuestion);
    }

    @RequestMapping("uploadQuestionFile")
    public boolean uploadQuestionFile(@RequestParam("file") MultipartFile file)  {
        if(file.isEmpty()){
            logger.info("文件不能为空，请重新上传");
            return false;
        }
        List<Question> questionList = new ArrayList<>();
        BufferedReader bufferedReader =null;
//        File tmpFile = new File("D:\\222.doc");

        try {
//            FileInputStream fileInputStream = file.getInputStream();
            HWPFDocument doc =new HWPFDocument(file.getInputStream());
            Range range = doc.getRange();
//            System.out.println(range.numParagraphs());
            Boolean isNewFile=true;
            Question question = null;
            Boolean flag=true;
            String questionStr=null;
            for (int i=0;i<range.numParagraphs();i++){

                Paragraph paragraph = range.getParagraph(i);

                String txt = paragraph.text().trim();
                if(StringUtils.isEmpty(txt) || txt.equals("\r")) continue;

                String choiceA = QuestionTools.getChoiceA(txt);
                String choiceB = QuestionTools.getChoiceB(txt);
                String choiceC = QuestionTools.getChoiceC(txt);
                String choiceD = QuestionTools.getChoiceD(txt);
                String choiceE = QuestionTools.getChoiceE(txt);
                String correctAnswer = QuestionTools.getCorrectAnswer(txt);
                if(choiceA != null){

                    question.setChoiceA(choiceA);
                }else if(choiceB != null){

                    question.setChoiceB(choiceB);

                }else if(choiceC != null){
                    question.setChoiceC(choiceC);
                }else if(choiceD != null){
                    question.setChoiceD(choiceD);
                }else if(choiceE != null){
                    question.setChoiceE(choiceE);
                }else if(correctAnswer != null){
                    question.setCorrectOption(correctAnswer);
                    question.setExperienceValue(10);
                    questionList.add(question);
                    isNewFile=true;
                }else{
                    if(isNewFile && isStartwithNumber(txt)){
                        question=new Question();
                        String [] str=txt.split("\\.|、",2);
                        questionStr = str[1].trim();
                        isNewFile=false;
                    } else{
                        questionStr += txt.trim();
                    }
                    if(range.getParagraph(i+1).text().startsWith("A.")||range.getParagraph(i+1).text().startsWith("A、")){
                        question.setQuestionDescription(questionStr);
                    }

                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        questionService.addQuestionListFromWord(questionList);

        return true;

}
    public static Boolean isStartwithNumber(String  str ){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str.charAt(0)+"");
        if(!isNum.matches()){
            return false;
        }
        return true;}


    public static void main(String[] args) {

//        List<Question> questionList = new ArrayList<>();
//        try {
//            FileInputStream fileInputStream = new FileInputStream("D:\\222.doc");
//            HWPFDocument doc =new HWPFDocument(fileInputStream);
//            Range range = doc.getRange();
////            System.out.println(range.numParagraphs());
//            Boolean isNewFile=true;
//            Question question = null;
//            Boolean flag=true;
//            String questionStr=null;
//            for (int i=0;i<range.numParagraphs();i++){
//
//                Paragraph paragraph = range.getParagraph(i);
//                String txt = paragraph.text();
//
//                //判断字符串以数字开头
////                if(isStartwithNumber(txt)&&(txt.substring(1,5).contains("、 ")||txt.substring(1,5).contains(". "))){
////
////                }
//                if(txt.startsWith("A.")||txt.startsWith("A、")){
//                    String [] str=txt.split("\\.",2);
//                    question.setChoiceA(str[1].trim());
//                }else if(txt.startsWith("B.")||txt.startsWith("B、")){
//                    String [] str=txt.split("\\.",2);
//                    question.setChoiceB(str[1].trim());
//
//                }else if(txt.startsWith("C.")||txt.startsWith("C、")){
//                    String [] str=txt.split("\\.",2);
//                    question.setChoiceC(str[1].trim());
//
//                }else if(txt.startsWith("D.")||txt.startsWith("D、")){
//                    String [] str=txt.split("\\.",2);
//                    question.setChoiceD(str[1].trim());
//                }else if(txt.startsWith("E.")||txt.startsWith("E、")){
//                    String [] str=txt.split("\\.",2);
//                    question.setChoiceE(str[1].trim());
//                }else if(txt.startsWith("答案")){
//                    String [] str=txt.split("\\s+",2);
//                    question.setCorrectOption(str[1].trim());
//                    questionList.add(question);
//                    isNewFile=true;
//                }else{
//                    if(isNewFile && isStartwithNumber(txt)){
//                        question=new Question();
//                        String [] str=txt.split("\\.|、",2);
//                        questionStr = str[1].trim();
//                        isNewFile=false;
//                    } else{
//                        questionStr += txt.trim();
//                    }
//                    if(range.getParagraph(i+1).text().startsWith("A.")||range.getParagraph(i+1).text().startsWith("A、")){
//                        question.setQuestionDescription(questionStr);
//                    }
//
//                }
//            }

//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        System.out.println(questionList);

    }


}
