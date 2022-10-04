package io.testprj.kopring_webflux.book.code

enum class BookCategoryCode (
    val categoryName: String, // 카테고리 명이 변경될 경우를 대비한 필드
    val categoryCode: String, // 원본 카테고리 코드
    val labelKr: String,  // 카테고리 라벨
){
    LITERATURE("LITERATURE", "LITERATURE", "문학"),
    FANTASY("FANTASY", "FANTASY", "판타지"),
    SCIENCE("SCIENCE", "SCIENCE", "과학"),
    IT_MOBILE("IT_MOBILE", "IT_MOBILE", "IT/모바일")
}