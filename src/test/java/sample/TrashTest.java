
package sample;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import services.PhaseService;
import domain.Phase;

public class TrashTest {

	/**
	 * @param args
	 */
	static PhaseService	phaseService;


	public static void main(final String[] args) {
		// TODO Auto-generated method stub
		//System.out.println(TrashTest.generadorDeTickers());
		final Phase p = TrashTest.phaseService.create(2696);
		System.out.println(p);
	}

	public static String generadorDeTickers() {
		String dateRes = "";
		String numericRes = "";
		final String alphanumeric = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdfghijklmnopqrstuvwxyz";
		dateRes = new SimpleDateFormat("yyMMdd").format(Calendar.getInstance().getTime());

		for (int i = 0; i < 6; i++) {
			final Random random = new Random();
			numericRes = numericRes + alphanumeric.charAt(random.nextInt(alphanumeric.length() - 1));
		}

		return dateRes + "-" + numericRes;
	}

}
