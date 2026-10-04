package Pekan4;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		ArrayList<Rekening> daftarRekening = new ArrayList<>(); 
		Rekening akunAktif = null; 
		boolean isRunning = true;
		
		System.out.println("=== SISTEM PERBANKAN MINI ===");
		
		while (isRunning) {
			System.out.println("\nMenu Utama : ");
			System.out.println("1. Buka Rekening Baru");
			System.out.println("2. Setor Tunai");
			System.out.println("3. Tarik Tunai");
			System.out.println("4. Cek Informasi Rekening");
			System.out.println("5. Ganti Akun");
			System.out.println("6. Cetak Mutasi (Riwayat)");
			System.out.println("7. Simulasi Akhir Bulan (Khusus Tabungan)");
			System.out.println("0. Keluar");
			System.out.print("Pilih Menu : ");
			
			int pilihan = input.nextInt();
			input.nextLine();
			
			switch (pilihan) {
			case 1 : 
				System.out.print("Masukkan No Rekening : ");
				String no = input.nextLine();
				System.out.print("Masukkan Nama Pemilik : ");
				String nama = input.nextLine();
				System.out.print("Masukkan Saldo Awal : ");
				double saldo = input.nextDouble();
				input.nextLine();
				System.out.print("Masukkan PIN : ");
				String pin = input.nextLine();
				
				System.out.println("Pilih Produk:");
				System.out.println("1. Tabungan Umum");
				System.out.println("2. Giro Bisnis");
				System.out.println("3. RekeningVIP");
				System.out.print("Pilihan : ");
				int pilihanProduk = input.nextInt();
				input.nextLine();
				
				Rekening rekeningBaru = null; // tipe Superclass
				
				if (pilihanProduk == 1) {
					System.out.print("Masukkan Suku Bunga (%) : ");
					double sukuBunga = input.nextDouble();
					input.nextLine();
					// Upcasting: objek RekeningTabungan disimpan ke variabel bertipe Rekening
					rekeningBaru = new RekeningTabungan(no, nama, saldo, pin, sukuBunga);
				} else if (pilihanProduk == 2) {
					System.out.print("Masukkan Batas Overdraft : ");
					double batasOverdraft = input.nextDouble();
					input.nextLine();
					// Upcasting: objek RekeningGiro disimpan ke variabel bertipe Rekening
					rekeningBaru = new RekeningGiro(no, nama, saldo, pin, batasOverdraft);
				} else if (pilihanProduk == 3) {
					saldo += 100000;
					System.out.println("Selamat! Anda mendapatkan bonus sebesar Rp.100.000");					
					System.out.println("Saldo awal anda menajdi" + saldo);
					
					daftarRekening.add(rekeningBaru);
					akunAktif = rekeningBaru;
					System.out.println("Rekening berhasil dibuka atas nama " + nama + "!");
				} else {
					System.out.println("Pilihan produk tidak valid!");
				}
				break;
				 
				
			case 2 : 
				if (akunAktif == null) {
					System.out.println("Error: Mohon maaf, anda belum memiliki nomor rekening!");
				} else {
					System.out.print("Masukkan nominal setor: ");
					double setor = input.nextDouble();
					akunAktif.setorTunai(setor); 
				}
				break;
				
			case 3 : 
				if (akunAktif == null) {
					System.out.println("Error: Mohon maaf, anda belum memiliki nomor rekening!");
				} else {
					System.out.println("Masukkan PIN : ");
					String pinTarik = input.nextLine();
					if (akunAktif.otentikasi(pinTarik)) {
                        System.out.print("Masukkan nominal tarik : ");
                        double tarik = input.nextDouble();
                        input.nextLine();
                        akunAktif.tarikTunai(tarik);
                    } else {
                    	System.out.println("PIN anda salah! Coba lagi.");
                    }
				}
				break; 
				
			case 4 :
				if (akunAktif == null) {
					System.out.println("Error: Anda belum membuka rekening!");
				} else {
					akunAktif.cekInformasi();
				}
				break;
				
			case 5 :
				if (daftarRekening.isEmpty()) {
					System.out.println("Error: Belum ada rekening yang terdaftar!");
					break;
				}
				System.out.print("Masukkan nomor rekening yang ingin diaktifkan: ");
				String noDicari = input.nextLine();
				
				Rekening hasilCari = null;
				for (Rekening r : daftarRekening) {
					if (r.getNomorRekening().equals(noDicari)) {
						hasilCari = r;
						break;
					}
				}
				
				if (hasilCari != null) {
					akunAktif = hasilCari;
					System.out.println("Berhasil! Akun aktif sekarang: " + akunAktif.getNomorRekening() + " (" + akunAktif.getNamaPemilik() + ")");
				} else {
					System.out.println("Error: Nomor rekening tidak ditemukan!");
				}
				break;
				
			case 6 : 
				if (akunAktif == null) {
					System.out.println("Error: Anda belum membuka rekening!");
				} else {
					System.out.println("Masukkan PIN : ");
					String pinMutasi = input.nextLine();
					if (akunAktif.otentikasi(pinMutasi)) {
                        akunAktif.cetakMutasi();
                    } else {
                    	System.out.println("PIN anda salah! Coba lagi");
                    }
				}
				break;	
				
			case 7 :
				if (akunAktif == null) {
					System.out.println("Error: Anda belum membuka rekening!");
				} else if (akunAktif instanceof RekeningTabungan) {
					// Downcasting: Rekening -> RekeningTabungan
					RekeningTabungan tabungan = (RekeningTabungan) akunAktif;
					tabungan.tambahBungaAkhirBulan();
				} else {
					System.out.println("Gagal: Fitur bunga akhir bulan hanya berlaku untuk Rekening Tabungan.");
				}
				break;
				
			case 0 :
				isRunning = false;
				System.out.println("Sistem ditutup. Terima Kasih!");
				break;
				
			default:
				System.out.println("Pilihan tidak valid!");
			}
		}
		input.close();
	}
}