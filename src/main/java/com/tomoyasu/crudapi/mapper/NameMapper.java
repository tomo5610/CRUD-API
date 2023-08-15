package com.tomoyasu.crudapi.mapper;

import com.tomoyasu.crudapi.entity.Name;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface NameMapper {
    @Select("SELECT * FROM names")
    List<Name> findAll();

    @Select("SELECT * FROM names WHERE id = #{id}")
    Optional<Name> findById(int id);

    @Insert("INSERT INTO names (id, name, birth) VALUES (#{id}, #{name}, #{birth})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertName(Name name);
}
