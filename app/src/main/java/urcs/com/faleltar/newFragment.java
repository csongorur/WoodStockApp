package urcs.com.faleltar;

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

public class newFragment extends Fragment
{
    private DatePicker date;
    private EditText size;
    private DatabaseHelper databaseHelper;
    private Button btnNew;
    private Spinner type;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        final View view = View.inflate(getActivity(), R.layout.new_fragment, null);

        date = (DatePicker) view.findViewById(R.id.newDatePicker);
        size = (EditText) view.findViewById(R.id.newSize);
        btnNew = (Button) view.findViewById(R.id.btnAdd);
        type = (Spinner) view.findViewById(R.id.newSpinner);
        databaseHelper = new DatabaseHelper(getActivity());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);

        btnNew.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int year = date.getYear();
                int month = date.getMonth()+1;
                int day = date.getDayOfMonth();
                String strType;

                String stDate = String.valueOf(year) + "." + String.valueOf(month) + "." + String.valueOf(day);

                if (type.getSelectedItem().toString().equals("Deszka"))
                {
                    strType = "1";
                }
                else
                {
                    strType = "2";
                }

                if (size.getText().toString().equals(""))
                {
                    Toast.makeText(getContext(), "Kérem adja meg a méretet.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    if (databaseHelper.insertData(stDate, size.getText().toString(), strType))
                    {
                        Toast.makeText(getContext(), "A hozzáadás sikeres", Toast.LENGTH_LONG).show();
                        ((MainActivity)getActivity()).toMainFragment();
                    }
                    else
                    {
                        Toast.makeText(getContext(), "A hozzáadás nem sikerült. Kérem probálja meg később.", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        return view;
    }


}
