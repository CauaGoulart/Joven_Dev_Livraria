package br.com.trier.Livraria.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
	
	private static DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public static LocalDateTime strToLocalDateTime(String dateStr) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    LocalDateTime localDateTime = LocalDateTime.parse(dateStr, formatter);
	    return localDateTime;
	}


	public static String localDateTimeToStr(LocalDateTime data) {
		return formatacao.format(data);
	}
}
