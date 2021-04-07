package cn.edu.zju.sishi.dao;

import cn.edu.zju.sishi.entity.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author sky
 * @create 2021-03-09 9:55
 */

@Mapper
@Component
public interface QuestionDao {

    @Select("select * from tb_question WHERE (is_public = true ${logicSymbol} 1=1)")
    List<Question> getQues (@Param("logicSymbol") String logicSymbol);

    @Select("select * from tb_question where question_id = #{question_id}")
    Question getQuesByID (String question_id);

    @Delete("delete from tb_question where question_id = #{question_id}")
    int deleteQuesByID (String question_id);

    //@Options(useGeneratedKeys = true,keyProperty = "question_id")
    @Insert("insert into tb_question(question_id, question_content, \"optionA\", \"optionB\",\"optionC\",\"optionD\",\"optionE\",answer) values(#{questionId},#{questionContent},#{optionA},#{optionB},#{optionC},#{optionD},#{optionE},#{answer})")
    int insertQues ( Question question);

    @Update({"<script>",
                "update tb_question",
                " <set>",
                    " <if test='questionContent != null'>question_content=#{questionContent},</if>",
                    " <if test='optionA != null'>\"optionA\"=#{optionA},</if>",
                    " <if test='optionB != null'>\"optionB\"=#{optionB},</if>",
                    " <if test='optionC != null'>\"optionC\"=#{optionC},</if>",
                    " <if test='optionD != null'>\"optionD\"=#{optionD},</if>",
                    " <if test='optionE != null'>\"optionE\"=#{optionE},</if>",
                    " <if test='answer != null'>answer=#{answer}</if>",
                " </set>",
                "where question_id = #{questionId}",
                "</script>"})
    int updateQues (Question question);

    @Update("UPDATE tb_question SET is_public = true WHERE question_id = #{question_id}")
    int updateIsPublicById(@Param("question_id") String question_id);

}
