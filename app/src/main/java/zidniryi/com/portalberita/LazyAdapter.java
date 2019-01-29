package zidniryi.com.portalberita;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

class LazyAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater = null;
    public ImageLoader imageLoader;

    public LazyAdapter(LokoUtama lokoUtama, ArrayList<HashMap<String, String>> berita) {
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.list_row, null);
        TextView id_berita = (TextView) vi.findViewById(R.id.kode);
        TextView judul = (TextView) vi.findViewById(R.id.judul);
        ImageView thumb_image = (ImageView)
                vi.findViewById(R.id.gambar);
        HashMap<String, String> daftar_berita = new HashMap<String,
                String>();
        daftar_berita = data.get(position);
        id_berita.setText(daftar_berita.get(LokoUtama.TAG_ID));
        judul.setText(daftar_berita.get(LokoUtama.TAG_JUDUL));

        imageLoader.DisplayImage(daftar_berita.get(LokoUtama.TAG_GAMBAR),thumb_image);
        return vi;
    }
}
