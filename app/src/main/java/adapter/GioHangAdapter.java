package adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.polymerchant.R;
import com.squareup.picasso.Picasso;
import java.text.DecimalFormat;
import java.util.List;
import model.GioHang;

public class GioHangAdapter extends ArrayAdapter<GioHang> {
    Activity context;
    int resource;
    List<GioHang> objects;

    public GioHangAdapter(Activity context, int resource, List<GioHang> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = this.context.getLayoutInflater();
            convertView = inflater.inflate(this.resource, null);
            viewHolder.img_giohang = convertView.findViewById(R.id.img_giohang);
            viewHolder.tv_tenmonan = convertView.findViewById(R.id.tv_tenmonan);
            viewHolder.tv_soluong = convertView.findViewById(R.id.tv_soluong);
            viewHolder.tv_giatien = convertView.findViewById(R.id.tv_giatien);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        GioHang gioHang = this.objects.get(position);
        try {
            Picasso.with(context).load(gioHang.getHinhanh()).into(viewHolder.img_giohang);
        } catch (Exception e) {
        }
        viewHolder.tv_tenmonan.setText(gioHang.getTenmonan());
        viewHolder.tv_soluong.setText("Số lượng: " + gioHang.getSoluong());
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        viewHolder.tv_giatien.setText(formatter.format(gioHang.getGia()));

        return convertView;
    }

    static class ViewHolder {
        ImageView img_giohang;
        TextView tv_tenmonan, tv_soluong, tv_giatien;
    }
}
