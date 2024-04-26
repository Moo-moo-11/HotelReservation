package org.example

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun main() {
    println("호텔 예약 프로그램입니다")

    var wantToEndSystem = false

    var isValidInitialInput = false
    var operator = "1"

    while(!wantToEndSystem) {

        while(!isValidInitialInput) {

            println("[메뉴]")
            println("1. 방예약, 2. 예약목록 출력, 3. 예약목록 (정렬) 출력, 4. 시스템 종료, 5. 금액 입금-출금 내역 목록 출력, 6. 예약 변경/취소")

            val input = readln()

            if (input == "1"|| input == "2"|| input == "3" || input == "4" || input == "5"|| input == "6") {
                operator = input
                isValidInitialInput = true
            } else {
                println("잘못 입력하셨습니다. 다시 입력해주세요")
            }
        }

        when (operator) {
            "1" -> {reserve()}
            "2" -> {
                reservationList.forEach {
                    val str = ("${reservationList.indexOf(it) + 1}. 이름: ${it["이름"]}, 방번호: ${it["방번호"]}, "
                            + "체크인 날짜: ${it["체크인 날짜"]?.substring(0..3)}-${it["체크인 날짜"]?.substring(4..5)}-${it["체크인 날짜"]?.substring(6..7)}, "
                            + "체크아웃 날짜: ${it["체크아웃 날짜"]?.substring(0..3)}-${it["체크아웃 날짜"]?.substring(4..5)}-${it["체크아웃 날짜"]?.substring(6..7)}")
                    println(str)
                }
            }
            "3" -> {println("미구현 상태입니다.")}
            "4" -> {
                wantToEndSystem = true
                println("시스템이 종료됩니다.")
            }
            "5" -> {println("미구현 상태입니다.")}
            "6" -> {println("미구현 상태입니다.")}
        }

        isValidInitialInput = false

    }

}

val reservationList: MutableList<Map<String,String>> = mutableListOf()

fun reserve() {
    var isValidName = false
    var name = ""

    println("예약자분의 성함을 입력해주세요")
    while(!isValidName) {
        val nameInput = readln()

        if (nameInput.isEmpty()) {
            println("이름은 빈칸이 될 수 없습니다")
        } else {
            name = nameInput
            isValidName = true
        }
    }

    var isValidRoomNumb = false
    var roomNumb = 0

    println("예약할 방 번호를 입력해주세요")
    while(!isValidRoomNumb) {
        val roomNumbInput = readln().toIntOrNull()

        if (roomNumbInput == null || roomNumbInput !in 100..999){
            println("올바르지 않은 방번호 입니다. 방번호는 100~999 영역 이내입니다")
        } else {
            roomNumb = roomNumbInput
            isValidRoomNumb = true
        }
    }

    val todayDate = SimpleDateFormat("yyyyMMdd", Locale.KOREAN).format(Calendar.getInstance().time)

    var isValidCheckInDate = false
    var checkInDate = "00000000"

    println("체크인 날짜를 입력해주세요 표기형식, 20230631")
    while(!isValidCheckInDate) {
        val checkInDateInput = readln()

        if (!(Regex("""^[0-9]{8}$""") matches checkInDateInput)) {
            println("숫자 8개만 입력이 가능합니다")
        } else if (!checkInDateInput.isDateValid()) {
            println("올바른 날짜가 아닙니다")
        } else if (checkInDateInput.toInt() < todayDate.toInt()) {
            println("체크인은 지난날은 선택할 수 없습니다")
        } else {
            checkInDate = checkInDateInput
            isValidCheckInDate = true
        }
    }

    var isValidCheckOutDate = false
    var checkOutDate = "00000000"

    println("체크아웃 날짜를 입력해주세요 표기형식, 20230631")
    while(!isValidCheckOutDate) {
        val checkOutDateInput = readln()

        if (!(Regex("""^[0-9]{8}$""") matches checkOutDateInput)) {
            println("숫자 8개만 입력이 가능합니다")
        } else if (!checkOutDateInput.isDateValid()) {
            println("올바른 날짜가 아닙니다")
        } else if (checkOutDateInput.toInt() <= checkInDate.toInt()) {
            println("체크아웃 날짜는 체크인 날짜와 같거나 지난날을 선택할 수 없습니다")
        } else {
            checkOutDate = checkOutDateInput
            isValidCheckOutDate = true
        }
    }


    reservationList.add(mapOf("이름" to name, "방번호" to roomNumb.toString(), "체크인 날짜" to checkInDate, "체크아웃 날짜" to checkOutDate))

    println("호텔 예약이 완료되었습니다.")

}

fun String.isDateValid(): Boolean {
    val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
    dateFormat.isLenient = false
    return try {
        dateFormat.parse(this)
        true
    } catch (e: ParseException) {
        false
    }
}