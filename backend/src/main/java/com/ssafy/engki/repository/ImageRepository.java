package com.ssafy.engki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.engki.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
	@Query(value = "select i from Image i where i.id in ("
		+ "select iw.id.imageId from ImageWord iw where iw.id.wordId = :wordId "
		+ "and 1 = (select count(iw2) from ImageWord iw2 where iw2.id.imageId = iw.id.imageId))")
	List<Image> getOneObjectImages(long wordId);
}
