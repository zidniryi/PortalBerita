package zidniryi.com.portalberita;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class LokoUtama extends Activity {
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> DaftarBerita = new ArrayList<HashMap<String, String>>();
    //Todo URL
    private static String url_berita = "http://192.168.95.56/portalberita/berita.php";
    public static final String TAG_ID = "id";
    public static final String TAG_JUDUL = "judul";
    public static final String TAG_GAMBAR = "gambar";

    JSONArray string_json = null;
    ListView list;
    LazyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loko_utama);


        DaftarBerita = new ArrayList<HashMap<String, String>>();
        new AmbilData().execeute();
        list = (ListView) findViewById(R.id.list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String, String> map = DaftarBerita.get(position);
// String new intent
                Intent in = new Intent(getApplicationContext(), DetailLoko.class);
                in.putExtra(TAG_ID, map.get(TAG_ID));
                in.putExtra(TAG_GAMBAR, map.get(TAG_GAMBAR));
                startActivity(in);
            }


        });
    }

    public void SetListViewAdapter(ArrayList<HashMap<String, String>> berita) {
        adapter = new LazyAdapter(this, berita);

    }

    private class AmbilData extends AsyncTask<String, String, String> {

        public void execeute() {

            pDialog = new ProgressDialog(LokoUtama.this);
            pDialog.setMessage("Mohon Tunggu");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            JSONObject json = jParser.makeHttpRequest(url_berita,
                    "GET", params);
            Log.i("Ini nilai json ", ">" + json);

            try {
                string_json = json.getJSONArray("berita");
                for (int i = 0; i < string_json.length(); i++) {
                    JSONObject c = string_json.getJSONObject(i);
                    String id_berita = c.getString(TAG_ID);
                    String judul = c.getString(TAG_JUDUL);
                    String link_image = c.getString(TAG_GAMBAR);
                    HashMap<String, String> map = new HashMap<String,
                            String>();
                    map.put(TAG_ID, id_berita);
                    map.put(TAG_JUDUL, judul);
                    map.put(TAG_GAMBAR, link_image);
                    DaftarBerita.add(map);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    SetListViewAdapter(DaftarBerita);
//Update Time.. // Current Date
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String formattedDate = df.format(c.getTime());
                    TextView updateTime = (TextView)
                            findViewById(R.id.update);
                    updateTime.setText("Terakhir di Update : " +
                            formattedDate);
                }

            });

        }

    }
}