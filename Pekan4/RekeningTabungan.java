package Pekan4;

public class RekeningTabungan extends Rekening{
	
	// atribut spesifik yang hanya dimiliki oleh tabungan
	private double sukuBunga;
	public double getRek() {
		return sukuBunga;
	}
	public void setRek(double sukuBunga) {
		this.sukuBunga = sukuBunga;
	}
	
	// construktor subclass
	public RekeningTabungan(String nomor, String nama, double saldoAwal, String pinAwal, double sukuBunga) {
		// super() memanggil construktor kelas induk (rekening). WAJIB berada di baris pertama!
		super(nomor, nama, saldoAwal, pinAwal);
		this.sukuBunga = sukuBunga;
	}

	public void tambahBungaAkhirBulan() {
		// Menghitung Bunga
	// mengapa bisa mengakses saldo secara langsung dari class RekeningTabungan?
		double nominalBunga = saldo * (sukuBunga / 100);
		saldo += nominalBunga;
		
		// mencatat riwayat transaksi
		String idTrx = "TRX-B-" + System.currentTimeMillis();
		riwayatTransaksi.add(new Transaksi(idTrx, "Bunga", nominalBunga));
		
		System.out.println("Bunga" + sukuBunga + "% berhasil ditambahkan Rp" + nominalBunga);
	}
}
