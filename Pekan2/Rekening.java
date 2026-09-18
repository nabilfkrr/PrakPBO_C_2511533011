package Pekan2;
import java.util.ArrayList;
import java.util.*;

public class Rekening {

	String nomorRekening;
	String namaPemilik;
	double saldo;
	
	ArrayList<Transaksi> riwayatTransaksi;
	
	public Rekening(String nomor, String nama, double saldoAwal) {
		this.nomorRekening = nomor;
		this.namaPemilik = nama;
		this.saldo = saldoAwal;
		
		this.riwayatTransaksi = new ArrayList<>();
		
		System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat dengan saldo Rp" + saldo);
	}
	
	public void setorTunai(double nominal) {
		if (nominal > 0) {
			saldo += nominal;
			
			String idTrx = "TRX-S-" + System.currentTimeMillis();
			Transaksi trxBaru = new Transaksi(idTrx, "Kredit", nominal);
			riwayatTransaksi.add(trxBaru);
			
			System.out.println("Setor tunai Rp" + nominal + " berhasil. Saldo saat ini : Rp" + saldo);
		} else {
			System.out.println("Gagal : Nominal setor harus lebih dari 0!");
		}
	}
	public void tarikTunai(double nominal) {
	    if (nominal >= 10000) {
	    	if (nominal > saldo) {
	    		
	    		System.out.println("Transaksi Gagal: Saldo tidak mencukupi. Saldo saat ini : Rp" + saldo );
	    	} else {
	        saldo -= nominal;
	        
	        String idTrx = "TRX-T-" + System.currentTimeMillis();
    		Transaksi trxBaru = new Transaksi(idTrx, "Debit", nominal);
    		riwayatTransaksi.add(trxBaru);
    		
	        System.out.println("Tarik tunai Rp" + nominal + " berhasil. Saldo saat ini : Rp" + saldo);
	    	}
	    	} else {
	        System.out.println("Transaksi Gagal : Minimal nominal penarikan Rp10.000");
	    }
	}
	
	public void cetakMutasi() {
		if (riwayatTransaksi.isEmpty()) {
			System.out.println("Belum ada transaksi pada rekening ini");
		} else {
			System.out.println("--- MUTASI REKENING ---");
			int totalTrx = riwayatTransaksi.size();
			
			 if (totalTrx > 3) {
				 System.out.println("(Menampilkan 3 transaksi terbaru dari " + totalTrx + " total transaksi)");
				 for (int total = totalTrx - 3; total < totalTrx; total++) {
		                riwayatTransaksi.get(total).cetakDetail();
				 }
			 } else {
				 for (Transaksi trx : riwayatTransaksi) {
				 trx.cetakDetail();
				 }
			 }
		}
	}
	
	public void cekInformasi() {
		System.out.println("--- INFO REKENING ---");
		System.out.println("No. Rekening : " + nomorRekening);
		System.out.println("Nama Pemilik : " + namaPemilik);
		System.out.println("Saldo Akhir : Rp" + saldo);
		System.out.println("--------------------");
	}
}
