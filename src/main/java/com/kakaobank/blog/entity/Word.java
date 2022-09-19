package com.kakaobank.blog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="word")
public class Word {
	@Id
	@Column(name="word_id")
    private Long word_id;
	@Column(name="word")
    private String word;
	@Transient
	@Column(name="partitioncode")
    private String partitioncode;
    
    @Builder
    public Word(Long word_id, String word, String partitionCd) {
        this.word_id = word_id;
        this.word = word;
        this.partitioncode = partitioncode;
    }
}
