package com.example.hzzhanyawei.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsTitleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsTitleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsTitleFragment extends Fragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ListView lv_title;
    private List<News> newses;
    private NewsAdaptar newsAdaptar;
    private boolean isTwoPane;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsTitleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsTitleFragment newInstance(String param1, String param2) {
        NewsTitleFragment fragment = new NewsTitleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public NewsTitleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_news_title, container, false);
        lv_title = (ListView) root.findViewById(R.id.news_title_list);
        lv_title.setAdapter(newsAdaptar);
        lv_title.setOnItemClickListener(this);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity().findViewById(R.id.news_content_layout) != null){
            isTwoPane = true;
        } else {
            isTwoPane = false;
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        newses = getNewses();
        newsAdaptar = new NewsAdaptar(activity, newses);
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private List<News> getNewses(){
        List<News> news = new ArrayList<News>();
        News news1 = new News();
        news1.setTitle("title one");
        news1.setContent("jjjsadjfkjakjfkajfdklajfkld;anjvfkl ndfjka;jkjkj;");
        news.add(news1);
        News news2 = new News();
        news2.setTitle("新闻2");
        news2.setContent("我是新闻2的内容，这是个测试");
        news.add(news2);
        return news;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        News data = newses.get(position);
        if(isTwoPane){
            NewsContentFragment newsContentFragment = (NewsContentFragment) getFragmentManager().findFragmentById(R.id.content_fragment);
            Log.d("ZYW", "_________________________________________________________ ");
            Log.d("ZYW", data.getTitle());
            Log.d("ZYW", data.getContent());
            Log.d("ZYW", newsContentFragment.toString());
            Log.d("ZYW", "_________________________________________________________ ");
            newsContentFragment.refreshContent(data.getTitle(), data.getContent());
        }else {
            NewsContentActivity.actionStart(getActivity(), data);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
