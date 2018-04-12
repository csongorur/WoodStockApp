package urcs.com.faleltar;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by ur on 03.05.2017.
 */

public class updateFragment extends Fragment
{
    private DatePicker date;
    private EditText size;
    private DatabaseHelper databaseHelper;
    private Button delete;
    private Button update;
    private Spinner type;
    private String id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = View.inflate(getActivity(), R.layout.update_fragment, null);
        date = (DatePicker) view.findViewById(R.id.updateDatePicker);
        size = (EditText) view.findViewById(R.id.updateSize);
        update = (Button) view.findViewById(R.id.btnUpdate);
        delete = (Button) view.findViewById(R.id.btnDelete);
        type = (Spinner) view.findViewById(R.id.updateSpinner);
        databaseHelper = new DatabaseHelper(getContext());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);

        id = getArguments().getString("id");

        Cursor cursor = databaseHelper.getOneData(id);

        String[] dateSep = cursor.getString(1).split("\\.");

        int year = Integer.parseInt(dateSep[0]);
        int month = Integer.parseInt(dateSep[1]) - 1;
        int day = Integer.parseInt(dateSep[2]);

        if (cursor.getString(3).equals("1"))
        {
            type.setSelection(0);
        }
        else
        {
            type.setSelection(1);
        }

        date.updateDate(year, month, day);
        size.setText(cursor.getString(2));

        update.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int year = date.getYear();
                int month = date.getMonth()+1;
                int day = date.getDayOfMonth();
                String strType;

                if (type.getSelectedItem().toString().equals("Deszka"))
                {
                    strType = "1";
                }
                else
                {
                    strType = "2";
                }

                String strDate = String.valueOf(year) + "." + String.valueOf(month) + "." + String.valueOf(day);
                String strSize = size.getText().toString();

                if (strSize.equals(""))
                {
                    Toast.makeText(getContext(), "Kérem adja meg a méretet.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    if (databaseHelper.updateData(id, strDate, strSize, strType))
                    {
                        Toast.makeText(getContext(), "A frissítés sikeres.", Toast.LENGTH_LONG).show();
                        ((MainActivity)getActivity()).toMainFragment();
                    }
                    else
                    {
                        Toast.makeText(getContext(), "A frissítés sikertelen. Kérem probálja meg később.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (databaseHelper.deleteData(id) > 0)
                {
                    Toast.makeText(getContext(), "A törlés sikeres.", Toast.LENGTH_LONG).show();
                    ((MainActivity)getActivity()).toMainFragment();
                }
                else
                {
                    Toast.makeText(getContext(), "A törlés sikertelen. Kérem probálja meg később.", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }
}
