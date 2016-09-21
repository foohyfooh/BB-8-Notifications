package com.foohyfooh.bb8.notifications;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AppInfoAdapter extends RecyclerView.Adapter<AppInfoAdapter.AppInfoViewHolder> {

    private final AppCompatActivity context;
    private final PackageManager packageManager;
    private final List<ApplicationInfo> infos;

    public AppInfoAdapter(AppCompatActivity context) {
        this.context = context;
        packageManager = context.getPackageManager();
        infos = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        Collections.sort(infos, new Comparator<ApplicationInfo>() {
            @Override
            public int compare(ApplicationInfo info1, ApplicationInfo info2) {
                return info1.loadLabel(packageManager).toString()
                        .compareTo(info2.loadLabel(packageManager).toString());
            }
        });
    }

    @Override
    public AppInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_info_item, parent, false);
        return new AppInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AppInfoViewHolder holder, int position) {
        final ApplicationInfo info = infos.get(position);
        holder.icon.setImageDrawable(info.loadIcon(packageManager));
        holder.icon.setContentDescription(String.format(context.getString(R.string.icon_description), info.loadLabel(packageManager)));
        holder.name.setText(info.loadLabel(packageManager));

        Config config = ConfigManager.getConfig(info.packageName);
        if (config != null){
            holder.enable.setVisibility(Button.GONE);
            holder.configure.setVisibility(Button.VISIBLE);
        }else{
            holder.enable.setVisibility(Button.VISIBLE);
            holder.configure.setVisibility(Button.GONE);
            holder.enable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ConfigManager.putConfig(info.packageName, new Config());
                    ConfigManager.insertIntoDatabase(view.getContext(), info.packageName, new Config());
                    holder.enable.setVisibility(Button.GONE);
                    holder.configure.setVisibility(Button.VISIBLE);
                }
            });
        }

        holder.configure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent configureIntent = new Intent(context, ConfigureActivity.class);
                configureIntent.putExtra(ConfigureActivity.EXTRA_PACKAGE_NAME, info.packageName);
                context.startActivity(configureIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class AppInfoViewHolder extends RecyclerView.ViewHolder{

        private final ImageView icon;
        private final TextView name;
        private final Button enable, configure;
        public AppInfoViewHolder(View view) {
            super(view);
            icon = (ImageView) view.findViewById(R.id.appInfo_icon);
            name = (TextView) view.findViewById(R.id.appInfo_name);
            enable = (Button) view.findViewById(R.id.appInfo_enable);
            configure = (Button) view.findViewById(R.id.appInfo_configure);
        }
    }
}
