package Pekan4;

public class RekeningGiro extends Rekening {
	private double batasOverdraft;
	
	public RekeningGiro(String nomor, String nama, double saldoAwal, String pinAwal, double batasOverdraft) {
		// memanggil inisialisasi dasar dari Superclass
		super(nomor, nama, saldoAwal, pinAwal);
		this.batasOverdraft = batasOverdraft;
	}
	
	// getter khusus Giro
	public double getBatasOverdraft() {
		return batasOverdraft;
	}
	
	// catatan : penarikan hingga limit overdraft akan kita selesaikan di modul 5
}
