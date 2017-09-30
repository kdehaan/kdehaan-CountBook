/*
https://stackoverflow.com/questions/16076985/listview-row-buttons-how-do-i-create-a-custom-adapter-that-connects-a-view-oncl
 */
package dehaan.kdehaan_countbook;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;


public class CounterListAdapter extends ArrayAdapter<Counter> {
    private List<Counter> counters;
    private int layoutResourceId;
    private Context context;

    public CounterListAdapter(Context context, int layoutResourceId, List<Counter> counters) {
        super(context, layoutResourceId, counters);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.counters = counters;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CounterHolder holder = null;

        holder = new CounterHolder();
        holder.Counter = counters.get(position);
        holder.removeCounterButton = (ImageButton)row.findViewById(R.id.counter_removeCounter);
        holder.removeCounterButton.setTag(holder.Counter);

        holder.name = (TextView)row.findViewById(R.id.counter_name);
        holder.value = (TextView)row.findViewById(R.id.counter_value);

        row.setTag(holder);
        setupItem(holder);
        return row;

    }

    private void setupItem(CounterHolder holder) {
        holder.name.setText(holder.Counter.getName());
        holder.value.setText(String.valueOf(holder.Counter.getCurrentValue()));

    }

    public static class CounterHolder {
        Counter Counter;
        TextView name;
        TextView value;
        ImageButton removeCounterButton;
    }

}