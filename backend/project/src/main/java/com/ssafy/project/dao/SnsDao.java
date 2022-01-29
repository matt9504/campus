package com.ssafy.project.dao;

import java.util.List;

import com.ssafy.project.dto.SnsDto;
import com.ssafy.project.dto.SnsImageDto;
import com.ssafy.project.dto.SnsParamDto;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
@Component
@Mapper
public interface SnsDao {
    public int snsInsert(SnsDto dto);
    public int snsDelete(int snsNo);
    public int snsUpdate(SnsDto dto);

    public List<SnsDto> snsList(SnsParamDto dto);
    public int snsListTotalCount();

    public List<SnsDto> snsListSearchWord(SnsParamDto dto);
    public int snsListSearchWordTotalCount();

    public int snsImageInsert(SnsImageDto dto);
    public List<SnsImageDto> snsImageList(int snsNo);
    // public int snsImageDelete(int snsNo);
    // public List<String> snsImageUrlDeleteList(int snsNo);
    // 이미지 업데이트는 지우고 다시 넣는 방식으로
    public int snsLikeInsert(
        @Param("snsNo") int snsNo,
        @Param("userNo") int userNo
    );
    public int snsLikeDelete(int snsNo, int userNo);
    public List<SnsDto> likeList(int userNo);

    public SnsDto snsDetail(SnsParamDto snsParamDto);

}