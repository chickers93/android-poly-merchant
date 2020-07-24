package adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.polymerchant.Home_Activity;
import com.example.polymerchant.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import model.Menu;

public class MenuAdapter extends ArrayAdapter<Menu> {
    Activity context;
    int resource;
    List<Menu> objects;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    public MenuAdapter(Activity context, int resource, List<Menu> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = this.context.getLayoutInflater();
            convertView = inflater.inflate(this.resource, null);
            viewHolder.img_menu = convertView.findViewById(R.id.img_menu);
            viewHolder.tv_tenmonan = convertView.findViewById(R.id.tv_tenmonan);
            viewHolder.tv_gia = convertView.findViewById(R.id.tv_gia);
            viewHolder.tv_chinhsua = convertView.findViewById(R.id.tv_chinhsua);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Menu menu = this.objects.get(position);
        if (menu.getTenmonan().length() < 20) {
            viewHolder.tv_tenmonan.setText(menu.getTenmonan());
        } else {
            viewHolder.tv_tenmonan.setText(menu.getTenmonan().substring(0, 20) + " ...");
        }
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        viewHolder.tv_gia.setText(formatter.format(menu.getGia()) + " VND");
        try {
            Picasso.with(context).load(menu.getHinhanh()).into(viewHolder.img_menu);
        } catch (Exception e) {

        }

        viewHolder.tv_chinhsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder altdial = new AlertDialog.Builder(context);
                final View dialogView = context.getLayoutInflater().inflate(R.layout.dialog_chinhsua_menu, null);
                altdial.setView(dialogView);

                final AlertDialog dialog = altdial.create();
                dialog.show();

                //ánh xạ
                TextView tv_tenmonan = dialogView.findViewById(R.id.tv_tenmonan);
                TextView tv_giacu = dialogView.findViewById(R.id.tv_giacu);
                final TextView ed_giamoi = dialogView.findViewById(R.id.ed_giamoi);
                Button btn_xacnhan = dialogView.findViewById(R.id.btn_xacnhan);

                //sự kiện
                tv_tenmonan.setText(objects.get(position).getTenmonan());
                DecimalFormat formatter = new DecimalFormat("###,###,###");
                tv_giacu.setText(String.valueOf(formatter.format(objects.get(position).getGia())));
                ed_giamoi.setText(String.valueOf(objects.get(position).getGia()));
                btn_xacnhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mData.child("Menu").child(Home_Activity.macuahang).child(objects.get(position).getMamonan()).child("gia").setValue(Integer.parseInt(ed_giamoi.getText().toString()));
                        dialog.cancel();
                        Toast.makeText(context, "Cập nhật giá thành công!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return convertView;
    }

    static class ViewHolder {
        ImageView img_menu;
        TextView tv_tenmonan, tv_gia, tv_chinhsua;
    }
}
