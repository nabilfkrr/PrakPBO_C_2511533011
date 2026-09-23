package Pekan3;
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
            System.out.println("7. Ganti PIN");
            System.out.println("0. Keluar");
            System.out.print("Pilih Menu : ");

            int pilihan = input.nextInt();
            input.nextLine();

            switch (pilihan) {
                case 1:
                    if (akunAktif != null && akunAktif.isDiblokir()) {
                        System.out.println("Akses Ditolak : Rekening aktif anda sedang diblokir!");
                        break;
                    }
                    System.out.print("Masukkan No Rekening : ");
                    String no = input.nextLine();
                    System.out.print("Masukkan Nama Pemilik : ");
                    String nama = input.nextLine();
                    System.out.print("Masukkan Saldo Awal : ");
                    double saldo = input.nextDouble();
                    input.nextLine();

                    String pin;
                    while (true) {
                        System.out.print("Masukkan PIN (6 digit) : ");
                        pin = input.nextLine();
                        if (Rekening.validasiPinStatic(pin)) break;
                    }

                    Rekening rekeningBaru = new Rekening(no, nama, saldo, pin);
                    daftarRekening.add(rekeningBaru);
                    akunAktif = rekeningBaru;
                    break;

                case 2:
                    if (akunAktif == null) {
                        System.out.println("Error : Anda belum memiliki nomor rekening!");
                    } else if (akunAktif.isDiblokir()) {
                        System.out.println("Akses Ditolak : Rekening anda diblokir!");
                    } else {
                        System.out.print("Masukkan nominal setor : ");
                        double setor = input.nextDouble();
                        input.nextLine();
                        akunAktif.setorTunai(setor);
                    }
                    break;

                case 3:
                    if (akunAktif == null) {
                        System.out.println("Error : Anda belum memiliki nomor rekening!");
                    } else if (akunAktif.isDiblokir()) {
                        System.out.println("Akses Ditolak : Rekening anda diblokir!");
                    } else {
                        System.out.print("Masukkan PIN : ");
                        String pinTarik = input.nextLine();
                        if (akunAktif.otentikasi(pinTarik)) {
                            System.out.print("Masukkan nominal tarik : ");
                            double tarik = input.nextDouble();
                            input.nextLine();
                            akunAktif.tarikTunai(tarik);
                        }
                    }
                    break;

                case 4:
                    if (akunAktif == null) {
                        System.out.println("Error : Anda belum membuka rekening!");
                    } else if (akunAktif.isDiblokir()) {
                        System.out.println("Akses Ditolak : Rekening anda diblokir!");
                    } else {
                        akunAktif.cekInformasi();
                    }
                    break;

                case 5:
                    if (daftarRekening.isEmpty()) {
                        System.out.println("Error : Belum ada rekening yang terdaftar!");
                        break;
                    }
                    System.out.print("Masukkan nomor rekening yang ingin diaktifkan : ");
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
                        System.out.println("Berhasil! Akun aktif : " + akunAktif.getNomorRekening() + " (" + akunAktif.getNamaPemilik() + ")");
                    } else {
                        System.out.println("Error : Nomor rekening tidak ditemukan!");
                    }
                    break;

                case 6:
                    if (akunAktif == null) {
                        System.out.println("Error : Anda belum membuka rekening!");
                    } else if (akunAktif.isDiblokir()) {
                        System.out.println("Akses Ditolak : Rekening anda diblokir!");
                    } else {
                        System.out.print("Masukkan PIN : ");
                        String pinMutasi = input.nextLine();
                        if (akunAktif.otentikasi(pinMutasi)) {
                            akunAktif.cetakMutasi();
                        }
                    }
                    break;

                case 7:
                    if (akunAktif == null) {
                        System.out.println("Error : Anda belum membuka rekening!");
                    } else if (akunAktif.isDiblokir()) {
                        System.out.println("Akses Ditolak : Rekening anda diblokir!");
                    } else {
                        System.out.print("Masukkan PIN saat ini : ");
                        String pinLama = input.nextLine();

                        String pinBaru;
                        while (true) {
                            System.out.print("Masukkan PIN baru (6 digit) : ");
                            pinBaru = input.nextLine();
                            if (Rekening.validasiPinStatic(pinBaru)) break;
                        }

                        System.out.print("Konfirmasi PIN baru : ");
                        String konfirmasiPin = input.nextLine();

                        if (!pinBaru.equals(konfirmasiPin)) {
                            System.out.println("Gagal : Konfirmasi PIN tidak cocok!");
                        } else {
                            akunAktif.gantiPin(pinLama, pinBaru);
                        }
                    }
                    break;

                case 0:
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