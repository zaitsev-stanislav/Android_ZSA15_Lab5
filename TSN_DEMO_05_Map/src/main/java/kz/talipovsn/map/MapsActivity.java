package kz.talipovsn.map;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import android.os.Handler;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap; // Карта
    private TextView textView; // Текстовый компонет

    private String markerTitle = ""; // Название выбранного маркера
    private String markerFileName = ""; // Имя файла с подробными данными выбранного маркера

    private final int SITYSCALE = 15; // Масштаб для отображения карты

    static final LatLng startMarker = new LatLng(52.2623354,76.954753); // Начальный маркер
    static final LatLng marker1 = new LatLng(52.265554, 76.9645598); // Маркер ПГУ
    static final LatLng marker2 = new LatLng(52.263568, 76.9574909); // Маркер ИнЕУ

    static final LatLng marker3 = new LatLng(52.2623354,76.954753); // Дом
    static final LatLng marker4 = new LatLng(52.2667085,76.9645806); // Арман
    static final LatLng marker5 = new LatLng(52.2667085,76.9645806); // Мадлен
    static final LatLng marker6 = new LatLng(52.2672009,76.9592377); // Средняя общеобразовательная школа №9
    static final LatLng marker7 = new LatLng(52.2648109,76.9594522); // Магазин Дания

    static final String CONFIG_FILE_NAME = "Config"; // Имя файла настроек приложения
    private SharedPreferences sPref; // Переменная для работы с настройками программы

    private static final String NORM = "isNorm";
    private static final String SPUTNIK = "isSputnik"; // Название ключа для хранения разрешения поиска
    private boolean isNorm = true; // Переменная признака выбора крупного шрифта с инициализацией
    private boolean isSputnik = false;// Переменная признака разрешения поиска с инициализацией
    private static final String RELEF = "isRelef"; // Название ключа для хранения разрешения поиска
    private boolean isRelef = false; // Переменная признака выбора крупного шрифта с инициализацией

    private int currentApiOS;

    private static final float ALPHA = 0.8f; // Коэффициент прозрачности для маркеров

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Доступ к карте
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        currentApiOS = android.os.Build.VERSION.SDK_INT;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sPref = getSharedPreferences(CONFIG_FILE_NAME, MODE_PRIVATE);

        if (savedInstanceState != null) {
            // Вторичное создание окна после переворачивания экрана
            isNorm = savedInstanceState.getBoolean(NORM, isNorm);
            isRelef = savedInstanceState.getBoolean(RELEF, isRelef);
            isSputnik = savedInstanceState.getBoolean(SPUTNIK, isSputnik);
        } else {
            // Первый запуск программы до переворачивания экрана
            // Чтение данных с настроек программы
            isNorm = sPref.getBoolean(NORM, isNorm);
            isRelef = sPref.getBoolean(RELEF, isRelef);
            isSputnik = sPref.getBoolean(SPUTNIK, isSputnik);
        }

        textView = findViewById(R.id.textViewInfo); // Доступ к компоненту "textViewInfo"
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Обычный тип карты
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Добавление маркера на карту с текстом
        mMap.addMarker(new MarkerOptions().position(startMarker).title((getString(R.string.startMarker_title))));

        // Добавление маркера на карту с текстом, иконкой и полупрозрачностью
        mMap.addMarker(new MarkerOptions().position(marker1).title(getString(R.string.marker1_title)).icon(
                        BitmapDescriptorFactory.fromResource(R.drawable.psu)).alpha(ALPHA).
                snippet(getString(R.string.marker1_txt_click)));

        mMap.addMarker(new MarkerOptions().position(marker2).title(getString(R.string.marker2_title)).icon(
                        BitmapDescriptorFactory.fromResource(R.drawable.ineu)).alpha(ALPHA).
                snippet(getString(R.string.marker2_txt_click)));

        // Добавление маркера на карту с текстом, иконкой и полупрозрачностью
        mMap.addMarker(new MarkerOptions().position(marker3).title((getString(R.string.marker3_title))).icon(
                        BitmapDescriptorFactory.fromResource(R.drawable.home)).alpha(ALPHA).
                snippet(getString(R.string.marker3_txt_click)));

        mMap.addMarker(new MarkerOptions().position(marker4).title((getString(R.string.marker4_title))).icon(
                        BitmapDescriptorFactory.fromResource(R.drawable.arman)).alpha(ALPHA).
                snippet(getString(R.string.marker4_txt_click)));

        mMap.addMarker(new MarkerOptions().position(marker5).title((getString(R.string.marker5_title))).icon(
                        BitmapDescriptorFactory.fromResource(R.drawable.madlen)).alpha(ALPHA).
                snippet(getString(R.string.marker5_txt_click)));

        mMap.addMarker(new MarkerOptions().position(marker6).title((getString(R.string.marker6_title))).icon(
                        BitmapDescriptorFactory.fromResource(R.drawable.school)).alpha(ALPHA).
                snippet(getString(R.string.marker6_txt_click)));

        mMap.addMarker(new MarkerOptions().position(marker7).title((getString(R.string.marker7_title))).icon(
                        BitmapDescriptorFactory.fromResource(R.drawable.daniya)).alpha(ALPHA).
                snippet(getString(R.string.marker7_txt_click)));

        //Разрешение изменения масштаба карты
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Проверка на включенный GPS и позиционирование на карте
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Показать текущее местоположение по GPS
            mMap.setMyLocationEnabled(true);
        }

        // Переход просмотра на карте на нужный маркер c зумом
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startMarker, SITYSCALE));

        // Инициализация стартового маркера
        onMarkerClick(getString(R.string.startMarker_id));

        // Обработчик нажатия на маркеры карты
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                MapsActivity.this.onMarkerClick(marker.getId());
                return true;
            }
        });
    }

    // Нажатие на маркер
    public void onMarkerClick(String idMarker) {
        switch (idMarker) {
            case "m0":
                doClickMarker(startMarker, getString(R.string.startMarker_info),
                        getString(R.string.startMarker_title), getString(R.string.startMarker_file));
                break;
            case "m1":
                doClickMarker(marker1, getString(R.string.marker1_info),
                        getString(R.string.marker1_title), getString(R.string.marker1_file));
                break;
            case "m2":
                doClickMarker(marker2, getString(R.string.marker2_info),
                        getString(R.string.marker2_title), getString(R.string.marker2_file));
                break;
            case "m3":
                doClickMarker(marker3, getString(R.string.marker3_info),
                        getString(R.string.marker3_title), getString(R.string.marker3_file));
                break;
            case "m4":
                doClickMarker(marker4, getString(R.string.marker4_info),
                        getString(R.string.marker4_title), getString(R.string.marker4_file));
                break;
            case "m5":
                doClickMarker(marker5, getString(R.string.marker5_info),
                        getString(R.string.marker5_title), getString(R.string.marker5_file));
                break;
            case "m6":
                doClickMarker(marker6, getString(R.string.marker6_info),
                        getString(R.string.marker6_title), getString(R.string.marker6_file));
                break;
            case "m7":
                doClickMarker(marker7, getString(R.string.marker7_info),
                        getString(R.string.marker7_title), getString(R.string.marker7_file));
                break;
        }
    }

    // Обработка нажатия на маркер
    public void doClickMarker(LatLng marker, String info, String markerTitle, String markerFileName) {
        this.markerTitle = markerTitle;
        this.markerFileName = markerFileName;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, SITYSCALE));
        findViewById(R.id.sv1).scrollTo(0, 0);
        if (Build.VERSION.SDK_INT >= 24) {
            textView.setText(Html.fromHtml(info, Html.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText(Html.fromHtml(info));
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(NORM, isNorm);
        savedInstanceState.putBoolean(SPUTNIK, isSputnik);
        savedInstanceState.putBoolean(RELEF, isRelef);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Сохранение настроек программы в файл настроек
        SharedPreferences.Editor ed = sPref.edit();
        ed.putBoolean(NORM, isNorm);
        ed.putBoolean(SPUTNIK, isSputnik);
        ed.putBoolean(RELEF, isRelef);
        ed.apply();
    }

    // Нажатие на кнопку маркера
    public void onClickButtonMarker(View view) {
        String idMarker = view.getTag().toString();
        onMarkerClick(idMarker);
    }

    //     Обработчик кнопки "Подробно"
    public void detailButtonClick(View view) {
        if (!markerFileName.equals("")) {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(getString(R.string.tMarker), markerTitle);
            intent.putExtra(getString(R.string.mfile), markerFileName);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), R.string.selectOb, Toast.LENGTH_SHORT).show();
        }
    }

    public void excursionButtonClick(View view) {

        exc(marker1, getString(R.string.marker1_info),
                getString(R.string.marker1_title), getString(R.string.marker1_file), 1000);
        exc(marker2, getString(R.string.marker2_info),
                getString(R.string.marker2_title), getString(R.string.marker2_file), 4000);
        exc(marker2, getString(R.string.marker2_info),
                getString(R.string.marker2_title), getString(R.string.marker2_file), 7000);
        exc(marker2, getString(R.string.marker2_info),
                getString(R.string.marker2_title), getString(R.string.marker2_file), 10000);
        exc(marker2, getString(R.string.marker2_info),
                getString(R.string.marker2_title), getString(R.string.marker2_file), 13000);
        exc(marker2, getString(R.string.marker2_info),
                getString(R.string.marker2_title), getString(R.string.marker2_file), 16000);
        exc(marker2, getString(R.string.marker2_info),
                getString(R.string.marker2_title), getString(R.string.marker2_file), 19000);

    }

    void exc(LatLng marker, String info, String markerTitle, String markerFileName,long delay) {
        Handler handler = new Handler();
            handler.postDelayed(() -> doClickMarker(marker, info, markerTitle, markerFileName),delay);
}


    @Override
    public  boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        MenuItem menuItemNorm = menu.findItem(R.id.norm);
        MenuItem menuItemSputnik = menu.findItem(R.id.sputnik);
        MenuItem menuItemRelef = menu.findItem(R.id.relef);

        if (isNorm) {
            menuItemNorm.setChecked(isNorm);
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        } else if (isSputnik) {
            menuItemSputnik.setChecked(isSputnik);
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        } else {
            menuItemRelef.setChecked(isRelef);
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.norm) {
            isNorm = true;
            isSputnik = false;
            isRelef = false;
            item.setChecked(isNorm);
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            return true;
        }

        if (id == R.id.sputnik) {
            isSputnik = true;
            isNorm = false;
            isRelef = false;
            item.setChecked(isSputnik);
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            return true;
        }

        if (id == R.id.relef) {
            isRelef = true;
            isNorm = false;
            isSputnik = false;
            item.setChecked(isRelef);
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
