package hr.perisic.luka.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import hr.perisic.luka.base.databinding.ItemLoadingStateBinding

class LoadingStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadingStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState, retry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            binding = ItemLoadingStateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class LoadStateViewHolder(
        private val binding: ItemLoadingStateBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {

        fun bind(loadState: LoadState, retry: () -> Unit) {
            binding.apply {
                when (loadState) {
                    is LoadState.Loading -> {
                        linearLayoutError.visibility = View.GONE
                        progressBarLoading.visibility = View.VISIBLE
                    }
                    is LoadState.Error -> {
                        linearLayoutError.visibility = View.VISIBLE
                        progressBarLoading.visibility = View.GONE
                        textViewError.text = loadState.error.localizedMessage
                        buttonRetry.setOnClickListener {
                            retry.invoke()
                        }
                    }
                    else -> {
                        linearLayoutError.visibility = View.GONE
                        progressBarLoading.visibility = View.GONE
                    }
                }
            }
        }

    }

}