package com.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

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
