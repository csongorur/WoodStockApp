package urcs.com.faleltar;

import android.graphics.Color;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by ur on 03.05.2017.
 */

public class mainFragment extends Fragment
{
    private ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        final View view = View.inflate(getActivity(), R.layout.main_fragment, null);
        listView = (ListView) view.findViewById(R.id.mainListView);
        DatabaseHelper databaseHelper  = new DatabaseHelper(getActivity());
        TextView deszka = (TextView) view.findViewById(R.id.totalDeszka);
        TextView foszni = (TextView) view.findViewById(R.id.totaFoszni);
        Button backup = (Button) view.findViewById(R.id.btnBackUp);

        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    File sd = Environment.getExternalStorageDirectory();
                    File data = Environment.getDataDirectory();

                    if (sd.canWrite()) {

                        String currentDBPath = "/data/data/" + getContext().getPackageName() + "/databases/" + DatabaseHelper.DATABASE_NAME;
                        String backupDBPath = "backupname.db";
                        File currentDB = new File(currentDBPath);
                        File backupDB = new File(sd, backupDBPath);

                        if (currentDB.exists()) {
                            FileChannel src = new FileInputStream(currentDB).getChannel();
                            FileChannel dst = new FileOutputStream(backupDB).getChannel();
                            dst.transferFrom(src, 0, src.size());
                            src.close();
                            dst.close();
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        deszka.setBackgroundColor(Color.parseColor("#81d4fa"));
        foszni.setBackgroundColor(Color.parseColor("#ef5350"));


        Cursor cursor = databaseHelper.getAllData();

        if (cursor.getCount() == 0)
        {
            deszka.setVisibility(View.INVISIBLE);
            foszni.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity(), "A lista üres", Toast.LENGTH_LONG).show();
        }
        else
        {
            deszka.setVisibility(View.VISIBLE);
            foszni.setVisibility(View.VISIBLE);

            if (databaseHelper.getTotalDeszka() == null)
            {
                deszka.setText("Jelenleg nincs raktáron deszka.");
            }
            else
            {
                deszka.setText("Deszka: " + databaseHelper.getTotalDeszka() + " köbméter");
            }

            if (databaseHelper.getTotalFoszni() == null)
            {
                foszni.setText("Jelenleg nincs raktáron foszni.");
            }
            else
            {
                foszni.setText("Foszni: " + databaseHelper.getTotalFoszni() + " köbméter");
            }

            Adapter adapter = new Adapter(getActivity(), databaseHelper.getAllData());
            listView.setAdapter(adapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                ((MainActivity)getActivity()).toUpdateFragment(String.valueOf(id));
            }
        });

        return view;
    }

    public class Adapter extends CursorAdapter
    {
        public Adapter(Context context, Cursor cursor)
        {
            super(context, cursor, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent)
        {
            return LayoutInflater.from(context).inflate(R.layout.main_list_item, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor)
        {
            TextView date = (TextView) view.findViewById(R.id.dateListItem);
            TextView size = (TextView) view.findViewById(R.id.sizeListItem);
            TextView type = (TextView) view.findViewById(R.id.typeListItem);
            LinearLayout layout = (LinearLayout) view.findViewById(R.id.listItem);


            date.setText("Dátum: " + cursor.getString(1));
            size.setText("Méret: " + cursor.getString(2) + " köbméter");

            if (cursor.getString(3).equals("1"))
            {
                type.setText("Tipus: Deszka");
                layout.setBackgroundColor(Color.parseColor("#64b5f6"));
            }
            else
            {
                type.setText("Tipus: Foszni");
                layout.setBackgroundColor(Color.parseColor("#f44336"));
            }
        }
    }
}
