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
public class Keyword {
	@Id
	@Column(name="keyword_id")
    private Long keyword_id;
	@Column(name="keyword")
    private String keyword;
	@Transient
	@Column(name="partitioncode")
    private String partitioncode;
    
    @Builder
    public Keyword(Long keyword_id, String keyword, String partitioncode) {
        this.keyword_id = keyword_id;
        this.keyword = keyword;
        this.partitioncode = partitioncode;
    }
}
