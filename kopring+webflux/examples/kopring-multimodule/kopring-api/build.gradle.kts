tasks.getByName("bootJar"){
    enabled = true
}

tasks.getByName("jar"){
    enabled = false
}

dependencies {
    apply(plugin = "kotlin-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("com.querydsl:querydsl-jpa:5.0.0")   // !!querydsl
    kapt("com.querydsl:querydsl-apt:5.0.0:jpa")         // !!querydsl
}