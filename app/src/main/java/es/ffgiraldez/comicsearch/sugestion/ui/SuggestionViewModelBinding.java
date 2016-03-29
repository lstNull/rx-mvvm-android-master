/*
 * Copyright (C) 2015 Fernando Franco Giráldez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.ffgiraldez.comicsearch.sugestion.ui;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.AdapterView;

import es.ffgiraldez.comicsearch.ui.binding.SubscribeActionOnAttachedStateChangeListener;
import es.ffgiraldez.comicsearch.sugestion.presentation.SuggestionViewModel;
import rx.functions.Action1;

public class SuggestionViewModelBinding {

    @BindingAdapter("model")
    public static void bindSearchView(final MaterialSearchView searchView, final SuggestionViewModel viewModel) {
        final SuggestionAdapter adapter = new SuggestionAdapter(viewModel);
        searchView.setAdapter(adapter);

        searchView.addOnAttachStateChangeListener(new SubscribeActionOnAttachedStateChangeListener<>(
                viewModel.didUpdateSuggestion(),
                new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        adapter.notifyDataSetChanged();
                        searchView.showSuggestions();
                    }
                }
        ));
    }

    @BindingAdapter("item_handler")
    public static void onItemClick(final MaterialSearchView searchView, final SuggestionViewModel viewModel) {
        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String suggestion = viewModel.suggestionAt(position);
                searchView.setQuery(suggestion, true);
                searchView.closeSearch();
            }
        });
    }
}
