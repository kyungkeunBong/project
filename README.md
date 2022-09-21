## 카카오뱅크 서버개발 사전과제

> 1. 블로그 검색 API 개발
> 2. 인기 검색어 목록 API 개발

### 요구사항

> 1. 블로그 검색 API 개발

a. 워드를 통해 블로그를 검색할 수 있어야 합니다.

b. 검색 결과에서 Sorting(정확도순, 최신순) 기능을 지원해야 합니다.

c. 검색 결과는 Pagination 형태로 제공해야 합니다.

d. 검색 소스는 카카오 API의 키워드로 블로그 검색(https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-blog)을 활용합니다.

e. 추후 카카오 API 이외에 새로운 검색 소스가 추가될 수 있음을 고려해야 합니다.

추가기능

f. 검색한 내역 db저장 (db 문제로 insert 실패시 error로그 작성후 검색한 결과 정보 전달)

g. 카카오 서버에러시 네이버 블로그 검색기능 구현.

> 2. 인기 검색어 목록 API 개발

a. 사용자들이 많이 검색한 순서대로, 최대 10개의 검색 키워드를 제공합니다.

b. 검색어 별로 검색 횟수도 함께 제공해야 합니다.

추가기능

c. 요일별 데이터를 넣어 스케쥴러를 통해, 에이징 처리가능.

### Prerequisites

1. JAVA 11 이상 또는 Kotlin 사용
2. Spring Boot 사용
3. Gradle 기반의 프로젝트
4. DB는 인메모리 DB(예: h2)를 사용하며 DB 컨트롤은 JPA로 구현
5. 외부 라이브러리 및 오픈소스 사용 가능
(단, README 파일에 사용한 오픈 소스와 사용 목적 명시)

### API Specifications

#### 블로그 검색 API / 'blog/search/list'

POST /blog/search/list

* Request

| Name  |  Type   |               Description                | Required | default  | 
|:-----:|:-------:|:----------------------------------------:|:--------:|:--------:|
| query | String  |               검색을 원하는 질의어                |    O     |   N/A    |
 | sort  | String  | 결과 문서 정렬 방식 accuracy(정확도순), recency(최신순) |    X     | accuracy |
 | page  | Integer |             결과 페이지번호 1~50의 값             |    X     |    1     |
 | size  | Integer |         한 페이지에 보여질 문서 수 1~50의 값          |    X     |    10    |

```json
{
    "query" : "kkbong"
}
```

* Response
1. Meta

|      Name      |  Type   |                         Description                         | 
|:--------------:|:-------:|:-----------------------------------------------------------:|
|  total_count   | Integer |                          검색된 문서 수                           |
| pageable_count | Integer |                  total_count 중 노출 가능 문서 수                   |
|     is_end     | Boolean | 현재 페이지가 마지막 페이지인지 여부, 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음 |

2. Documents

|   Name    |  Type  |                          Description                          | 
|:---------:|:------:|:-------------------------------------------------------------:|
|   title   | String |                           블로그 글 제목                            |
| contents  | String |                           블로그 글 요약                            |
|    url    | String |                           블로그 글 URL                           |
| blogname  | String |                            블로그의 이름                            |
| thumbnail | String |     검색 시스템에서 추출한 대표 미리보기 이미지 URL, 미리보기 크기 및 화질은 변경될 수 있음      |
| datetime  | String | 블로그 글 작성시간, ISO 8601 [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz] |

```json
{
  "meta": {
    "total_count": 2,
    "pageable_count": 2,
    "is_end": true
  },
  "documents": [
    {
      "title": "[깨깨봉이야기] 몰아쓰는 초보주부이야기 :: 201503",
      "contents": "번 타고싶네여 몹쓸 비행기병 :) 그나저나 3월은 그냥 집에서 떡볶이 만들다가 한달이 훌쩍 지나가버린 느낌적인 느낌!?!? 우리집에 떡볶이 먹으러 놀러올래욤??? 아참, 인스타 아이디 바꿨어용 <b>kkbong</b>_gram (깨깨봉그램) 자 세한 얘기는 여행포스팅으로 풀어볼까하는데 과연 흐흐흐흐흐흐 4월이야기로 다시돌아올께요...",
      "url": "https://blog.naver.com/painapple35/220336081036",
      "blogname": "깨소금맛 깨깨봉 이야기♡",
      "thumbnail": "https://search3.kakaocdn.net/argon/130x130_85_c/G9sIhsofvBU",
      "datetime": "2015-04-20T12:43:00.000+09:00"
    },
    {
      "title": "안양네일/범계네일 :: 크리스마스네일 피오레네일 고고씽",
      "contents": "째지지 몹니까??? ​ 아무래도 자유부인이어서였나봉가?? 떠아야 엄마 행복했떠~ 이미 이번주가 크리스마스 지만 겨울은 아직 길게 남았으니까 겨울느낌적인 느낌으로 기분전환하러 피오레네일 어떠세용? 그럼, 저는 잊어버릴만 하면 뭔가를 들고 다시 나타나겠습니다 흐흣 가깝게 인스타에서 만나도 좋아요! <b>kkbong</b>_gram 뿅뿅",
      "url": "https://blog.naver.com/painapple35/220892607963",
      "blogname": "깨소금맛 깨깨봉 이야기♡",
      "thumbnail": "https://search4.kakaocdn.net/argon/130x130_85_c/52HWRVujXuE",
      "datetime": "2016-12-22T22:01:00.000+09:00"
    }
  ]
}
```

* Error

| 에러 코드 | 설명                 |
|:------|--------------------|
| 401   | Rest Api Header 오류 |


#### 인기 검색어 목록 / '/blog/keyword/top'

GET /blog/keyword/top

* Response

keywords

|  Name   |  Type  | Description | 
|:-------:|:------:|:-----------:|
| keyword | String |   검색한 키워드   |
|  count  | String |     횟수      |



```json
{
  "keywords": [
    {
      "keyword": "test",
      "count": "52"
    },
    {
      "keyword": "검색1",
      "count": "14"
    },
    {
      "keyword": "검색8",
      "count": "10"
    },
    {
      "keyword": "검색7",
      "count": "9"
    },
    {
      "keyword": "검색5",
      "count": "7"
    },
    {
      "keyword": "검색2",
      "count": "7"
    },
    {
      "keyword": "검색13",
      "count": "5"
    },
    {
      "keyword": "검색6",
      "count": "5"
    },
    {
      "keyword": "검색9",
      "count": "4"
    },
    {
      "keyword": "검색4",
      "count": "3"
    }
  ]
}
```
### Entity
#### Word
- 검색어 목록을 가지고 있음.
```
keyword_id : bigint (PK / GeneratedValue) 키워드 ID
keyword : varchar 키워드
partitioncode : varchar 에이징 삭제를 위한 요일값 세팅
```

### 추가한 library 

- slf4j-api
  - 로그 설정을 위해 사용
- httpclient:4.5.13
  - http 통신을 위해 사용





