package com.example.unlistedi.torajatourism.data;

import java.util.ArrayList;

public class RequestParemeter {
    public static ArrayList<String> index_header;

    public static String URL = "http://192.168.1.104/toraja-tourism/";

    public static String get_all_index_header(){
        String result;
        result = URL + "androidapi/index_header/";
        return result;
    }

    public static String get_assets_index_header(){
        String result;
        result = URL + "assets/img/index_header/";
        return result;
    }
    public static String get_lokasi_wisata_by_id(int i){
        String result;
        result = URL + "androidapi/detailwisata/"+ String.valueOf(i);
        return result;
    }

    public static String get_tujuan_populer(){
        String result;
        result = URL + "androidapi/tujuan_populer/";
        return result;
    }

    public static String get_all_ambar_wisata(int id_lokasi_wisata){
        String result;
        result = URL + "androidapi/gambar_detail_wisata/"+String.valueOf(id_lokasi_wisata);
        return result;
    }
    public static String get_asset_gambar_wisata(){
        String result;
        result = URL + "assets/img/lokasiwisata/";
        return result;
    }
    public static String get_jam_operasional(int id_lokasi_wisata){
        String result;
        result = URL + "androidapi/jam_operasional/"+String.valueOf(id_lokasi_wisata);
        return result;
    }
    public static String get_hari_operasional(int id_lokasi_wisata){
        String result;
        result = URL + "androidapi/hari_operasional/"+String.valueOf(id_lokasi_wisata);
        return result;
    }
}
