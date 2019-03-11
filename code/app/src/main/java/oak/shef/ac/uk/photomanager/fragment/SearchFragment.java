/*created by Chennan Luo, Weishi Li in 2018-2019*/

package oak.shef.ac.uk.photomanager.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import oak.shef.ac.uk.photomanager.R;

public class SearchFragment extends Fragment {
    @Nullable
    private ResultFragment showresult = new ResultFragment();
    private TextView inDate;
    private TextView inTIL;
    private TextView inDes;
    private String date_keyword;
    private String title_keyword;
    private String description_keyword;
    private ImageButton dIB;
    private Button search_button;
    private java.util.Calendar c;
    private DatePickerDialog dPD;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View searchview = inflater.inflate(R.layout.fragment_search, null);
        search_button = (Button) searchview.findViewById(R.id.search_button);
        inDate = (TextView) searchview.findViewById(R.id.text_indate);
        inTIL = (TextView) searchview.findViewById(R.id.text_intitle);
        inDes = (TextView) searchview.findViewById(R.id.text_indescription);
        dIB = (ImageButton) searchview.findViewById(R.id.imageButton_ser_dat);

        dIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = java.util.Calendar.getInstance();
                int day = c.get(java.util.Calendar.DAY_OF_MONTH);
                int month = c.get(java.util.Calendar.MONTH);
                int year = c.get(java.util.Calendar.YEAR);

                dPD = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int dYear, int dMonth, int dDay) {
                        inDate.setText(dDay + "/" + (dMonth+1) + "/" + dYear);
                    }
                },day,month,year);
                dPD.show();

            }
        });
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                date_keyword = String.valueOf(inDate.getText());
                title_keyword = String.valueOf(inTIL.getText());
                description_keyword = String.valueOf(inDes.getText());
                showresult = ResultFragment.newInstance(date_keyword,title_keyword,description_keyword);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,showresult).commit();
            }

        });
        return searchview;
    }
}
