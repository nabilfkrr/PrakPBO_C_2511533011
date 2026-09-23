package Pekan3;
import java.util.ArrayList;

public class Rekening {

    private String nomorRekening;
    private String namaPemilik;
    private double saldo;
    private String pin;
    private int percobaan = 0;
    private boolean diblokir = false;
    private ArrayList<Transaksi> riwayatTransaksi;

    public Rekening(String nomor, String nama, double saldoAwal, String pinAwal) {
        this.nomorRekening = nomor;
        this.namaPemilik = nama;
        this.saldo = saldoAwal;
        this.pin = pinAwal;
        this.riwayatTransaksi = new ArrayList<>();
        System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat dengan saldo Rp" + saldo);
    }

    public String getNomorRekening() {
        return nomorRekening;
    }

    public String getNamaPemilik() {
        return namaPemilik;
    }

    public boolean isDiblokir() {
        return diblokir;
    }

    public boolean otentikasi(String inputPin) {
        if (diblokir) {
            System.out.println("Akses Ditolak : Rekening ini telah diblokir!");
            return false;
        }

        if (this.pin.equals(inputPin)) {
            percobaan = 0;
            return true;
        } else {
            percobaan++;
            System.out.println("PIN salah! Percobaan ke-" + percobaan + " dari 3.");
            if (percobaan >= 3) {
                diblokir = true;
                System.out.println("Rekening DIBLOKIR : Terlalu banyak percobaan PIN salah!");
            }
            return false;
        }
    }

    public void setorTunai(double nominal) {
        if (nominal > 0) {
            saldo += nominal;
            riwayatTransaksi.add(new Transaksi("TRX-S-" + System.currentTimeMillis(), "Kredit", nominal));
            System.out.println("Setor tunai Rp" + nominal + " berhasil. Saldo saat ini : Rp" + saldo);
        } else {
            System.out.println("Gagal : Nominal setor harus lebih dari 0!");
        }
    }

    public void tarikTunai(double nominal) {
        if (nominal < 10000) {
            System.out.println("Transaksi Gagal : Minimal nominal penarikan Rp10.000");
            return;
        }
        if (nominal > saldo) {
            System.out.println("Transaksi Gagal : Saldo tidak mencukupi. Saldo saat ini : Rp" + saldo);
            return;
        }
        saldo -= nominal;
        riwayatTransaksi.add(new Transaksi("TRX-T-" + System.currentTimeMillis(), "Debit", nominal));
        System.out.println("Tarik tunai Rp" + nominal + " berhasil. Saldo saat ini : Rp" + saldo);
    }

    public void cekInformasi() {
        System.out.println("--- INFO REKENING ---");
        System.out.println("No. Rekening : " + nomorRekening);
        System.out.println("Nama Pemilik : " + namaPemilik);
        System.out.println("Saldo Akhir  : Rp" + saldo);
        System.out.println("---------------------");
    }

    public void cetakMutasi() {
        if (riwayatTransaksi.isEmpty()) {
            System.out.println("Belum ada transaksi pada rekening ini.");
            return;
        }

        System.out.println("--- MUTASI REKENING ---");
        int totalTrx = riwayatTransaksi.size();

        if (totalTrx > 3) {
            System.out.println("(Menampilkan 3 transaksi terbaru dari " + totalTrx + " total transaksi)");
            for (int i = totalTrx - 3; i < totalTrx; i++) {
                riwayatTransaksi.get(i).cetakDetail();
            }
        } else {
            for (Transaksi trx : riwayatTransaksi) {
                trx.cetakDetail();
            }
        }
    }

    public boolean gantiPin(String pinLama, String pinBaru) {
        if (!this.pin.equals(pinLama)) {
            System.out.println("Gagal : PIN lama yang anda masukkan salah!");
            return false;
        }
        if (pinLama.equals(pinBaru)) {
            System.out.println("Gagal : PIN baru tidak boleh sama dengan PIN lama!");
            return false;
        }
        if (!validasiPinStatic(pinBaru)) {
            return false;
        }
        this.pin = pinBaru;
        System.out.println("PIN berhasil diubah!");
        return true;
    }

    public static boolean validasiPinStatic(String pin) {
        if (!pin.matches("\\d{6}")) {
            System.out.println("Gagal : PIN harus 6 digit angka!");
            return false;
        }

        boolean semuaSama = true;
        for (int i = 1; i < pin.length(); i++) {
            if (pin.charAt(i) != pin.charAt(0)) {
                semuaSama = false;
                break;
            }
        }
        if (semuaSama) {
            System.out.println("Gagal : PIN tidak boleh semua angka sama!");
            return false;
        }

        boolean urutanNaik = true;
        boolean urutanTurun = true;
        for (int i = 1; i < pin.length(); i++) {
            if (pin.charAt(i) != pin.charAt(i - 1) + 1) urutanNaik = false;
            if (pin.charAt(i) != pin.charAt(i - 1) - 1) urutanTurun = false;
        }
        if (urutanNaik || urutanTurun) {
            System.out.println("Gagal : PIN tidak boleh berurutan (contoh: 123456 atau 654321)!");
            return false;
        }
        
        if ((pin.substring(0, 2).repeat(3)).equals(pin)) {
            System.out.println("Gagal : PIN tidak boleh pola 2 angka berulang (contoh: 121212)!");
            return false;
        }

        if ((pin.substring(0, 3).repeat(2)).equals(pin)) {
            System.out.println("Gagal : PIN tidak boleh pola 3 angka berulang (contoh: 123123)!");
            return false;
        }

        boolean pasangan = true;
        for (int i = 0; i < pin.length(); i += 2) {
            if (pin.charAt(i) != pin.charAt(i + 1)) {
                pasangan = false;
                break;
            }
        }
        if (pasangan) {
            System.out.println("Gagal : PIN tidak boleh pola pasangan berulang (contoh: 112233)!");
            return false;
        }

        return true;
    }
}