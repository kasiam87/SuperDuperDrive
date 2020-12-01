package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<File> getFiles(Integer userId);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFile(Integer fileId);

    @Insert("INSERT INTO FILES (fileId, filename, contenttype, filesize, userid, filedata) " +
            "VALUES (#{fileId}, #{filename}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int addFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void deleteFile(Integer fileId);
}
