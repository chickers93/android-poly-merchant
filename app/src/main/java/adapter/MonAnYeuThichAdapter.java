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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.polymerchant.Home_Activity;
import com.example.polymerchant.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import model.Menu;
import model.Menu_temp;
import model.MonAnYeuThich;

public class MonAnYeuThichAdapter extends ArrayAdapter<Menu_temp> {
    Activity context;
    int resource;
    List<Menu_temp> objects;

    public MonAnYeuThichAdapter(Activity context, int resource, List<Menu_temp> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = this.context.getLayoutInflater();
            convertView = inflater.inflate(this.resource, null);
            viewHolder.img_menu = convertView.findViewById(R.id.img_menu);
            viewHolder.tv_tenmonan = convertView.findViewById(R.id.tv_tenmonan);
            viewHolder.tv_soluot = convertView.findViewById(R.id.tv_soluot);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Menu_temp menu_temp = this.objects.get(position);
        if (menu_temp.getTenmonan().length() < 20) {
            viewHolder.tv_tenmonan.setText(menu_temp.getTenmonan());
        } else {
            viewHolder.tv_tenmonan.setText(menu_temp.getTenmonan().substring(0, 20) + " ...");
        }

        viewHolder.tv_soluot.setText(String.valueOf(menu_temp.getSoluong()));

        try {
            Picasso.with(context).load(menu_temp.getHinhanh()).into(viewHolder.img_menu);
        } catch (Exception e) {

        }


        return convertView;
    }

    static class ViewHolder {
        ImageView img_menu;
        TextView tv_tenmonan, tv_soluot;
    }

}
