package com.va181.hidayatulloh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.constraintlayout.solver.ArrayRow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_film";
    private final static String TABLE_FILM = "t_film";
    private final static String KEY_ID_FILM = "ID_Film";
    private final static String KEY_JUDUL = "Judul";
    private final static String KEY_TGL = "Tanggal";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_AKTOR= "Aktor";
    private final static String KEY_GENRE = "Genre";
    private final static String KEY_SINOPSIS = "Sinopsis";
    private final static String KEY_LINK = "Link";
    private SimpleDateFormat sdFromat = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault());
    private Context context;

    public DatabaseHandler(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_BERITA = "CREATE TABLE " + TABLE_FILM
                + "(" + KEY_ID_FILM + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUL + " TEXT, " + KEY_TGL + " DATE, "
                + KEY_GAMBAR + " TEXT, " + KEY_AKTOR + " TEXT, "
                + KEY_GENRE+ " TEXT, " + KEY_SINOPSIS + " TEXT, "
                + KEY_LINK + " TEXT);";

        db.execSQL(CREATE_TABLE_BERITA);
        inisialisasiFilmAwal(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_FILM;
        db.execSQL(DROP_TABLE);
        onCreate(db);

    }

    public void tambahFilm(Film dataFilm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, sdFromat.format(dataFilm.getTanggal()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_AKTOR, dataFilm.getAktor ());
        cv.put(KEY_GENRE, dataFilm.getGenre ());
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis ());
        cv.put(KEY_LINK, dataFilm.getLink());

        db.insert(TABLE_FILM, null, cv);
        db.close();
    }

    public void tambahFilm(Film dataFilm, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, sdFromat.format(dataFilm.getTanggal()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_AKTOR, dataFilm.getAktor ());
        cv.put(KEY_GENRE, dataFilm.getGenre ());
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis ());
        cv.put(KEY_LINK, dataFilm.getLink());
        db.insert(TABLE_FILM, null, cv);
    }

    public void editFilm(Film dataFilm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL,  dataFilm.getJudul());
        cv.put(KEY_TGL, sdFromat.format( dataFilm.getTanggal()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_AKTOR,  dataFilm.getAktor ());
        cv.put(KEY_GENRE,  dataFilm.getGenre ());
        cv.put(KEY_SINOPSIS,  dataFilm.getSinopsis ());
        cv.put(KEY_LINK,  dataFilm.getLink());

        db.update(TABLE_FILM, cv, KEY_ID_FILM + "=?", new String[]{String.valueOf(dataFilm.getIdFilm ())});

        db.close();
    }

    public void  hapusFilm(int idFilm) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_FILM, KEY_ID_FILM + "=?", new String[]{String.valueOf(idFilm)});
        db.close();
    }

    public ArrayList<Film> getAllFilm() {
        ArrayList<Film> dataFilm = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FILM;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if (csr.moveToFirst()){
            do{
                Date tempDate = new Date();
                try {
                    tempDate = sdFromat.parse(csr.getString(2));
                } catch (ParseException er) {
                    er.printStackTrace();
                }

                Film tempFilm = new Film (
                        csr.getInt(0),
                        csr.getString(1),
                        tempDate,
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6),
                        csr.getString(7)
                );

                dataFilm.add(tempFilm);
            } while (csr.moveToNext());
        }

        return dataFilm;

    }

    private String storeImageFile(int id) {
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return  location;
    }

    private void inisialisasiFilmAwal(SQLiteDatabase db) {
        int idFilm = 0;
        Date tempDate = new Date();

        //Menambah data film ke-1
        try {
            tempDate = sdFromat.parse("09/07/2009 ");
        } catch (ParseException er) {
            er.printStackTrace();
        }



        Film film1 = new Film (
                idFilm,
                "Punk In Love",
                tempDate,
                storeImageFile(R.drawable.film1),
                "Vino G. Bastian\n" +
                "Andhika Pratama\n" +
                "Yogi Finanda\n" +
                "Aulia Sarah\n" +
                "Catherine Wilson\n" +
                "Davina Veronica\n" +
                "Girindra Kara\n" +
                "Adipati Dolken",
                "Komedi",
                "Almira (Aulia Sarah), Arok (Vino G. Bastian), Mojo (Yogi Finanda), dan Yoji (Andhika Pratama) adalah 4 anak punk yang berasal dari Malang. Suatu hari, Arok berniat bunuh diri dengan meloncat dari kantor Departemen Agama setelah mendapat kabar bahwa pujaan hatinya Maia (Girindra Kara) hendak menikah dengan pemuda lain di Jakarta 5 hari lagi. Untunglah aksi tersebut dapat dicegah oleh 3 sahabatnya, lalu Arok berinisiatif ke Jakarta untuk menggagalkan pernikahan Maia dan menyatakan cinta kepadanya. Setelah berpamitan dengan ibu Mojo (Hani) di kuburan, keempat anak punk tersebut ikut naik truk merah menuju Yogyakarta, dan dari sana mereka berencana mencari tumpangan lain ke Jakarta.\n" +
                "Malangnya, mereka terbawa truk yang salah. Alih-alih ke Yogya, mereka malah terbawa ke Gunung Bromo. Malam hari terpaksa mereka lewatkan dengan tidur di emperan warung. Paginya, mereka membantu pemilik warung bersih-bersih, sambil bersih-bersih Arok mengutil 1 cincin dipikirnya sebagai tanda cintanya kepada Maia. Setelah bersih-bersihnya selesai sebagai imbalannya, mereka dijamu makan di sana. Menumpang jeep, mereka berempat menuju ke barat. Jeep tersebut membawa mereka ke Makam Bung Karno di Blitar. Mojo yang mengagumi sosok Bung Karno mendeklamasikan teks proklamasi di depan makam. Setelah itu, mereka menaiki mobil rusak yang diderek ke Cepu. Di dalam mobil, mereka berdiskusi hebat mengenai masalah anti-kemapanan.\n" +
                        "Di tempat tujuan, mereka bertemu dengan seorang penjual sate Madura (Suro) dan meminta buatkan 40 tusuk sate. Namun karena keempat anak punk tadi memberi tukang sate itu dengan uang Rp. 6.000,-, tukang sate itu marah sambil menghunus celurit. Hampir saja keempat anak punk itu dihabisi kalau Arok tidak meneriakkan kata-kata penyesalan untuk Maia karena tak berhasil menyatakan cinta. Tukang sate itu mengampuni mereka atas dasar kesamaan nasib. Dirinya gagal menikah dengan pujaan hatinya Tiwi karena orang tua Tiwi berniat menjodohkan anaknya dengan seorang juragan garam. Tidak itu saja, mereka dibolehkan membeli 20 tusuk sate dan empat lontong seharga Rp. 6.000,- asalkan mereka sendiri yang membakar. Almira, Arok, Mojo, dan Yoji meneruskan perjalanan ke Semarang dengan menumpang minibus. Di tengah perjalanan, Yoji merasa ingin buang air besar akibat sate yang dimakan semalam. Akhirnya, Yoji buang air besar di jendela, tetapi tinjanya malah mengenai mobil di belakangnya, yang ternyata dikemudikan anggota TNI-AD (Rudy). Sebagai hukuman, Arok, Mojo, dan Yoji dihukum push-up di depan sebuah kelenteng, sementara Almira disuruh membersihkan tinja yang bercokol di kaca mobil.\n" +
                        "Mereka meneruskan perjalanan ke Rembang. Di permukiman Tionghoa di pinggir pantai, Mojo melihat poster bergambar Yoji yang sedang main basket. Mojo tertawa karena merasa pose Yoji konyol, sehingga mengundang 3 sahabatnya mendekat. Setelah mengamati, Almira dan Arok juga ikut tertawa. Tinggallah Yoji yang marah dan meninggalkan mereka ke pinggir pantai. Almira menyusul dan mengatakan kalau Yoji terlihat menjijikkan di poster itu. Akhirnya, Yoji ikut tertawa.\n" +
                        "Setelah itu, mereka menumpang truk pengangkut tepung terigu ke Semarang. Di Semarang, mereka turun di suatu tempat yang sedang dilanda banjir. Terpaksalah Arok, Mojo, dan Yoji berjalan sambil menggotong Almira yang tentunya tidak mungkin menanggalkan pakaian bawah. Akhirnya, mereka semua tercebur ke air karena Yoji merasa ada yang lewat di kakinya. Malam harinya, mereka menumpang kereta api barang setelah membantu membereskan sampah yang dibawa seorang pemulung tua (Saputra). Selama perjalanan, Arok bermimpi disodomi oleh bapak-bapak (Hartawan) yang memisahkan dirinya dengan Maia dan teman-temannya menertawakannya. Setiba di Stasiun Notog (Banyumas), mereka menumpang mobil ambulans yang membawa mereka ke Cirebon. Perjalanan ini diwarnai dengan sopir (Rombeng) yang terkantuk-kantuk, sehingga ugal-ugalan dalam mengemudikan mobil. Keempat anak punk itu ketakutan. Mengikuti Mojo, mereka berdoa kepada Tuhan agar selamat dalam perjalanan, padahal sebelumnya Almira, Arok, dan Yoji mengingkari keberadaan Tuhan.\n" +
                        "Setiba di Cirebon, mereka semua kelaparan. Yoji dan Almira mengamen di jalanan dengan menyanyi dangdut dan berjoget. Arok dan Mojo awalnya tidak mau ikut karena malu ketahuan bergaya dangdut oleh grup punk lain, tetapi demi perut akhirnya mereka turut pula meramaikan. Seusai makan nasi bungkus, Almira kelabakan karena datang bulan. Mereka berempat segera datang ke sebuah warung membeli 2 pembalut, dan pemilik warung (Otig Pakis) menyediakan 2 bungkus. Arok merobek salah satu bungkusan dan menunjukkan 2 lembar pembalut karena uangnya kurang. Pemilik warung marah dan menuntut mereka membayar bungkusan yang dirobek. Lalu Almira terlibat bisik-bisik dengan Arok dan Mojo dan merencanakan untuk melempar uang dan membawa lari bungkusan, sementara Yoji berusaha membujuk pemilik warung. Tak dinyana, karena mendengar 3 anak punk itu berbisik-bisik dalam bahasa Jawa dialek Arekan/Jawa Timuran, pemilik warung itu mengizinkan 2 pembalut bungkus itu dibawa karena ternyata ia berasal dari Malang.\n" +
                        "Pada malam harinya, Arok dkk. hendak meneruskan perjalanan ke Jakarta, tetapi ternyata Mojo tampak lemah. Setelah diperiksa, ternyata luka di kakinya – akibat terjatuh saat menggotong Almira di Semarang – terinfeksi kuman tetanus. Mereka pun datang ke klinik terdekat, namun ditolak masuk oleh resepsionis (Andhika Dharmapermana) dan satpam yang beralasan klinik penuh. Sambil mengeluarkan sumpah serapah, Arok putus asa dan hendak pulang ke Malang, karena percuma saja membawa serta Mojo yang sedang sekarat ke Jakarta. Terbata-bata Mojo berkata untuk jangan pulang ke Malang, karena akan sia-sia saja bila dirinya kelak mati bila sahabatnya gagal meraih keinginannya. Akhirnya, Arok dan Yoji berinisiatif menculik dokter klinik (Aline Jusria) tersebut yang baru pulang kerja, dan memintanya mengobati Mojo.\n" +
                        "Mereka akhirnya tiba di Stasiun Jatinegara, Jakarta. Memasuki jalanan yang padat, Arok menunjukkan cincin yang dicurinya dari toko cenderamata di Bromo untuk diserahkan kepada Maia. Ketiga sahabatnya marah karena semestinya cincin itu bisa dijual untuk makan. Mojo yang emosi menonjok muka Arok, dan tanpa sengaja menubruk seorang pejalan kaki. Pejalan kaki itu menubruk seseorang yang duduk di warung, yang ternyata Leo (Dendy Subangil), preman di wilayah itu. Leo menghajar si pejalan kaki, yang kemudian menunjuk Arok sebagai orang yang menubruknya. Akhirnya Leo melepas pejalan kaki, dan gantian menyerang Arok dan membuatnya terkapar. Polisi keburu datang, lalu Leo melarikan diri bersama anak buahnya. Sebelum itu, ia sempat membawa cincin Arok yang terjatuh.\n" +
                        "Jadilah keempat anak punk itu masuk penjara. Atas bujukan Maia, Yoji menghubungi Tante Rossa (Catherine Wilson) yang dahulu membawanya menjadi model. Tante Rossa mengeluarkan keempat anak punk itu dengan memberi jaminan, dengan syarat Yoji harus ikut 3 kali sesi pemotretan. Yoji awalnya enggan, tetapi akhirnya menyanggupi. Mereka segera pergi ke tempat pengantin. Di tengah jalan, Arok melihat Leo sedang berada di warung bersama anak buahnya. Arok meminta mobil berhenti, dan keluar lalu menantang Leo berkelahi. Setelah itu, ia segera melarikan diri bersama dengan 3 sahabatnya dan Tante Rossa. Leo dkk. mengejar, dan mencegat mereka berlima di sebuah perkampungan. Lalu datang Ekay (Ade Habibie) dan anak buahnya. Ekay menyuruh agar Arok dan Leo menyelesaikan masalahnya sendiri menggunakan tangan kosong. Pada awal pertarungan, Arok babak belur, tetapi setelah Maia datang dan memberi semangat, Arok terbakar semangatnya dan bertubi-tubi menghajar Leo sampai babak belur. Akhirnya Arok mendapatkan cincinnya dan memasangkannya ke jari Maia sambil menyatakan cinta. Maia ternyata juga mencintai Arok. Mendadak, seorang pemuda bernama Andra (Dallas Pratama) yang sedianya hendak menikah dengan Maia bertanya kepada calon isterinya itu, pilih Arok atau dirinya. Maia memilih Arok, tetapi itu malah membuat Andra bersyukur karena sesungguhnya dirinya belum siap menikah. Itulah sebabnya, mengapa selama beberapa hari sebelum hari pernikahannya, Andra ogah-ogahan mengurusi persiapan nikah dan tenggelam dalam aktivitas grup musiknya. Bersamaan itu pula Yoji menyatakan cintanya pada Almira.\n" +
                        "Film ditutup dengan Arok dan Maia yang sedang mengandung menyambut pelanggan di depan Warung Maia Arok yang didirikannya, Yoji yang menjadi model, dan Mojo – yang dahulunya penggali kubur – mewujudkan impiannya menjadi aktor.",
                "https://www.youtube.com/watch?v=jR8faJAVO0o"
        );

        tambahFilm(film1, db);
        idFilm++;

        // Data film ke-2
        try {
            tempDate = sdFromat.parse("26/08/2011 ");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Film film2 = new Film (
                idFilm,
                "Tendangan Dari Langit",
                tempDate,
                storeImageFile(R.drawable.film2),
                "Irfan Bachdim sebagai Irfan\n" +
                        "Kim Jeffrey Kurniawan sebagai Kim\n" +
                "Maudy Ayunda sebagai Indah\n" +
                "Giorgino Abraham sebagai Hendro\n" +
                "Jordi Onsu sebagai Mitro\n" +
                "Joshua Suherman sebagai Purnomo\n" +
                "Agus Kuncoro sebagai Pak Lik Hasan\n" +
                "Sujiwo Tejo sebagai Pak Darto\n" +
                "Natasha Chairani sebagai Melly\n" +
                "Yosie Kristanto sebagai Wahyu\n" +
                "Marco Dandy sebagai Richard\n" +
                "Timo Scheunemann sebagai Coach Timo\n" +
                "Matias Ibo sebagai Matias",
                 "Drama Keluarga",
                  "Wahyu (16 tahun) memiliki kemampuan luar biasa dalam bermain sepak bola. Ia tinggal di Desa Langitan di lereng gunung Bromo bersama ayahnya seorang penjual minuman hangat di kawasan wisata gunung api itu, dan ibunya.\n" +
                          "Demi membahagiakan orang tuanya, Wahyu memanfaatkan keahliannya dalam bermain bola dengan menjadi pemain sewaan dan bermain bola dari satu tim desa ke tim desa lain dengan bantuan Hasan, pamannya. Sayangnya Pak Darto, ayah Wahyu sangat tidak menyukai apa yang dilakukan anaknya.\n" +
                          "Suatu hari saat Wahyu bermain bola dengan rekan-rekannya, keahlian istimewanya tak sengaja dilihat oleh Coach Timo yang tengah hiking bersama Matias di lereng Bromo. Pelatih Timo kemudian menawari Wahyu untuk datang ke Malang dan menjalani tes bersama Persema Malang.\n" +
                          "Sayangnya, berbagai ujian dalam meraih kesempatan emas bermain bersama Irfan Bachdim dan Kim Kurniawan di Persema mendapat banyak halangan. Selain harus memilih antara cintanya kepada Indah dan impiannya untuk bermain bola di jenjang yang lebih tinggi, Wahyu juga harus mampu meyakinkan Pak Darto. Belum lagi ternyata Hasan memiliki kepentingannya sendiri terhadap Wahyu.\n" +
                          "Selain berbagai rintangan yang harus ia hadapi, layaknya seorang pemain bola sebelum mencetak gol, Wahyu juga harus menghadapi tantangan terakhir dari dirinya sendiri. Sebuah penyakit yang biasa menyerang anak-anak usia enam belas tahun seperti Wahyu.",
                "https://www.youtube.com/watch?v=sjAg4HiJpBM"
        );

        tambahFilm(film2, db);
        idFilm++;

        //Data film ke-3
        try {
            tempDate = sdFromat.parse("17/04/2008 ");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Film film3 = new Film (
                idFilm,
                "The Tarik Jabrix",
                tempDate,
                storeImageFile(R.drawable.film3),
                "Tria Changcut sebagai Caca Sutarya / Cacing\n" +
                        "Erick Changcut sebagai Dadang Modip\n" +
                "Qibil Changcut sebagai Coki\n" +
                "Alda Changcut sebagai Ciko\n" +
                "Dipa Changcut sebagai Mulder\n" +
                "Francine Roosenda sebagai Mayang\n" +
                "Carissa Putri sebagai Callista\n" +
                "Sellen Fernandez sebagai Pak Rohim\n" +
                "Andrew sebagai Valdin\n" +
                "Ario Bayu sebagai Max\n" +
                "Dian Dipa Chandra\n" +
                "Ricky Cuaca",
                "Komedi",
                "Caca Sutarya (Tria Changcut) atau yang biasa dipanggil dengan sebutan \"Cacing\" adalah seorang lelaki yang enerjik dan ingin menjadi anggota The Road Devil, geng motor paling brutal dan ditakuti di Kota Bandung. Tapi ia tidak sampai hati untuk melakukan ujian yang diberikan karena tidak sesuai dengan hati nuraninya Akhirnya, ia mengajak teman-teman dekatnya Dadang Modip (Erick Changcut), Coki (Qibil Changcut), Ciko (Alda Changcut) serta Mulder (Dipa Changcut). Mereka membentuk geng motor dengan nama \"The Tarix Jabrix\" yang sering nongkrong di \"Bengkel Sugema\" milik Pak Rohim (Sellen Fernandez), ayah dari Dadang. Seorang perempuan magang di bengkel itu, bernama Mayang (Francine Roosenda). Teman-teman Dadang menaruh hati pada Mayang. Sementara, Cacing sedang mendekati Callista (Carissa Putri), primadona sekolah. Sayangnya Callista sering dijemput pacarnya, Valdin , teman kakaknya, Max (Ario Bayu), yang merupakan pentolan geng motor The Smokers, sebuah geng motor yang lebih besar. Diam-diam Callista menaruh hati pada Cacing yang dianggap lucu, menyenangkan dan enak diajak ngobrol.\n" +
                        "Kedekatan Callista dan Cacing membuat hubungan Callista dan Valdin renggang, Valdin tidak terima. Akhirnya, The Tarix Jabrix berseteru dengan The Smokers. Cacing memberitahu Callista, bahwa ada anak buah kakaknya yang membawa narkoba. Cacing dkk kemudian berinisiatif untuk membongkar kasus ini, setelah Callista mengatakan bahwa The Smokers adalah geng motor yang bersih. Niat baik The Tarix Jabrix untuk membongkar kasus ini lagi-lagi mendapat halangan dari Valdin dan Max. The Smokers merasa bahwa Cacing hanya mencari gara-gara. Kedua geng ini berseteru, mereka siap tawuran meski jumlah The Smokers lebih unggul dan persiapan Cacing hanya satu hari.",
                "https://www.youtube.com/watch?v=DZhotG5Svkg"
        );

        tambahFilm(film3, db);
        idFilm++;

        // Data film ke-4
        try {
            tempDate = sdFromat.parse("30/05/2013 ");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Film film4 = new Film (
                idFilm,
                "Sank Kiai",
                tempDate,
                storeImageFile(R.drawable.film4),
                "Ikranagara sebagai KH Hasyim Asy'ari\n" +
                        "Christine Hakim sebagai Masrurah/Nyai Kapu\n" +
                        "Agus Kuncoro sebagai KH Wahid Hasyim\n" +
                        "Adipati Dolken sebagai Harun\n" +
                        "Meriza Febriani sebagai Sari\n" +
                        "Dimas Aditya sebagai Hamzah\n" +
                        "Royham Hidayat sebagai Khamid\n" +
                        "Ernestsan Samudera sebagai Abdi\n" +
                        "Ayes Kassar sebagai Baidhowi\n" +
                        "Dayat Simbaia sebagai KH Yusuf Hasyim\n" +
                        "Dymas Agust sebagai KH Mas Mansur\n" +
                        "Andrew Trigg sebagai Brigadir Mallaby\n" +
                        "Arswendi Nasution sebagai KH. A. Wahab Hasbullah\n" +
                        "Norman Rivianto Akyuwen sebagai kang Solichin",
                "Drama, Sejarah",
                "Pendudukan Jepang ternyata tidak lebih baik dari Belanda. Jepang mulai melarang pengibaran bendera merah putih, melarang lagu Indonesia Raya dan memaksa rakyat Indonesia untuk melakukan Sekerei (menghormat kepada Matahari). KH Hasyim Asyari sebagai tokoh besar agamis saat itu menolak untuk melakukan Sekerei karena beranggapan bahwa tindakan itu menyimpang dari aqidah agama Islam. Menolak karena sebagai umat Islam, hanya boleh menyembah kepada Allah SWT. Karena tindakannya yang berani itu, Jepang menangkap KH Hasyim Asyari.\n" +
                        "KH Wahid Hasyim, salah satu putra dia mencari jalan diplomasi untuk membebaskan KH Hasyim Asyari. Berbeda dengan Harun, salah satu santri KH Hasyim Asyari yang percaya cara kekerasanlah yang dapat menyelesaikan masalah tersebut. Harun menghimpun kekuatan santri untuk melakukan demo menuntut kebebasan KH Hasyim Asyari. Tetapi harun salah karena cara tersebut malah menambah korban berjatuhan.\n" +
                        "Dengan cara damai KH Wahid Hasyim berhasil memenangkan diplomasi terhadap pihak Jepang dan KH Hasyim Asyari berhasil dibebaskan. Ternyata perjuangan melawan Jepang tidak berakhir sampai disini. Jepang memaksa rakyat Indonesia untuk melimpahkan hasil bumi. Jepang menggunakan Masyumi yang diketuai KH. Hasyim Asy'ari untuk menggalakkan bercocok tanam. Bahkan seruan itu terselip di ceramah sholat Jum'at. Ternyata hasil tanam rakyat tersebut harus disetor ke pihak Jepang. Padahal saat itu rakyat sedang mengalami krisis beras, bahkan lumbung pesantren pun nyaris kosong. Harun melihat masalah ini secara harfiah dan merasa bahwa KH. Hasyim Asy'ari mendukung Jepang, hingga ia memutuskan untuk pergi dari pesantren.\n" +
                        "Jepang kalah perang, Sekutu mulai datang. Soekarno sebagai presiden saat itu mengirim utusannya ke Tebuireng untuk meminta KH Hasyim Asyari membantu mempertahankan kemerdekaan. KH Hasyim Asyari menjawab permintaan Soekarno dengan mengeluarkan Resolusi Jihad yang kemudian membuat barisan santri dan masa penduduk Surabaya berduyun duyun tanpa rasa takut melawan sekutu di Surabaya. Gema resolusi jihad yang didukung oleh semangat spiritual keagamaan membuat Indonesia berani mati.\n" +
                        "Di Jombang, Sarinah membantu barisan santri perempuan merawat korban perang dan mempersiapkan ransum. Barisan laskar santri pulang dalam beberapa truk ke Tebuireng. KH Hasyim Asyari menyambut kedatangan santri- santrinya yang gagah berani, tetapi air mata mengambang di matanya yang nanar.",
                "https://www.youtube.com/watch?v=m31tzVZ_bGE"
        );

        tambahFilm(film4, db);
        idFilm++;

        //Data film ke-5
        try {
            tempDate = sdFromat.parse ( "10/10/2013" );
        } catch (ParseException er) {
            er.printStackTrace ();
        }
        Film film5 = new Film (
                idFilm,
                "Manusia Setengah Salmon",
                tempDate,
                storeImageFile ( R.drawable.film5 ),
                "Raditya Dika sebagai Dika\n" +
                        "Kimberly Ryder sebagai Patricia\n" +
                        "Eriska Rein sebagai Jessica\n" +
                        "Bucek Depp sebagai Papa Dika\n" +
                        "Dewi Irawan sebagai Mama Dika\n" +
                        "Mo Sidik sebagai Editor Buku\n" +
                        "Insan Nur Akbar sebagai Sugiman\n" +
                        "Dimas Gabra sebagai Dika kecil\n" +
                        "Griff Pradapa sebagai Edghar\n" +
                        "Lolita Balani sebagai Yuditha\n" +
                        "Lana Girlly sebagai Ingga\n" +
                        "Lani Girlly sebagai Anggi\n" +
                        "Randhika Djamil sebagai Resky\n" +
                        "Dimzy sebagai Dekki\n" +
                        "Maya Otos sebagai Mama Patricia\n" +
                        "Soleh Solihun sebagai Kosasi",
                "Drama, Komedi",
                "Ketika ibunya (Dewi Irawan) memutuskan untuk pindah dari rumah semasa dia kecil, Dika (Raditya Dika), seorang penulis, justru berusaha pindah dari hal-hal yang selama ini dia susah untuk lepaskan: cerita cintanya yang lama dengan Jessica (Eriska Rein) hingga hubungannya dengan bapaknya (Bucek Depp).\n" +
                        "Meskipun kurang menyetujui rencana ibunya pindahan, namun Dika tetap membantu. Mereka mencari rumah yang baru, satu demi satu. Pengalaman lucu mereka dapatkan ketika rumah yang mereka kunjungi ternyata tidak ada yang cocok. Satu rumah ada yang jelek banget, rumah lainnya ada kuburan dan bekas orang gantung diri. Mereka sempat putus asa, sampai akhirnya Dika menemukan sebuah rumah, yang menurut ibunya sempurna. Di saat ini juga Dika bertemu dengan Patricia (Kimberly Ryder) seorang cewek nan cantik, dan mulai melakukan pendekatan.\n" +
                        "Masalah timbul ketika sudah pindah rumah, karena Dika tidak menyukai rumah barunya. Kenangan dengan rumah lama masih membekas. Sementara itu, hubungan Dika dengan Patricia juga terganggu, karena mantan Dika, Jessica tanpa dia sadari masih membayang-bayanginya.\n" +
                        "Hal menggelikan terjadi ketika Dika mendapatkan sopir (Insan Nur Akbar) yang bau ketek, dan menyebabkan Dika mengalami dilema untuk memecatnya atau mengorbankan paru-parunya. Adik Dika, Edgar (Griff Pradapa), juga bersiap untuk ujian nasional dengan cara-cara yang aneh. Kelakuan bapaknya yang ingin dekat dengannya, tetapi melalui cara yang mengesalkan Dika, juga membuat momen tersebut jadi komedi yang mengharukan.\n" +
                        "Dika pada akhirnya menyadari bahwa perjalanannya untuk pindah rumah, juga merupakan perjalanan dia untuk berpindah dari hal-hal yang selama ini menahan dia untuk tumbuh menuju kedewasaan. Ternyata keputusan untuk berkomitmen, adalah keputusan untuk berpindah seperti rombongan jutaan Salmon yang menempuh perjalanan 1.448 km untuk kawin, dibayangi berbagai ancaman predator.",
                "https://www.youtube.com/watch?v=HNVZ0HhfFeY"
        );

        tambahFilm (film5,db );

    }
}