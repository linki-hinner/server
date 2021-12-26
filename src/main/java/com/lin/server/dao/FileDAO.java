package com.lin.server.dao;

import com.lin.server.dto.FileInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface FileDAO {
    @Insert({"INSERT INTO `server`.`file` (`uuid`, `name`, `size`, `type`, `createTime`, `url`)",
            " VALUES (#{uuid}, #{name}, #{size}, #{type}, #{createTime}, #{url})"})
    void addFileInfo(FileInfo fileInfo);

    @Select({"SELECT * FROM `file` WHERE `uuid` = #{uuid}"})
    FileInfo getFileInfo(@Param("uuid")String uuid);
}
