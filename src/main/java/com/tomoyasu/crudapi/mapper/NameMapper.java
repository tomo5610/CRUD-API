package com.tomoyasu.crudapi.mapper;

import com.tomoyasu.crudapi.entity.Name;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Optional;

@Mapper
public interface NameMapper {
    @Select("SELECT * FROM names")
    List<Name> findAll();

    @Select("SELECT * FROM names WHERE id = #{id}")
    Optional<Name> findById(int id);

    @Insert("INSERT INTO names (name, birth) VALUES (#{name}, #{birth})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createName(Name createName);

    @Update("UPDATE names SET name = #{name}, birth = #{birth} WHERE id = #{id}")
    void updateName(Name updateName);

    @Delete("DELETE FROM names WHERE id = #{id}")
    void deleteById(int id);
}
