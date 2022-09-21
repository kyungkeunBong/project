package com.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="word")
public class Word {
	@Id
	@Column(name="keyword_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keyword_id;
	@Column(name="keyword")
    private String keyword;
	@Transient
	@Column(name="partitioncode")
    private String partitioncode;
    
    @Builder
    public Word(Long keyword_id, String keyword, String partitioncode) {
        this.keyword_id = keyword_id;
        this.keyword = keyword;
        this.partitioncode = partitioncode;
    }
}
