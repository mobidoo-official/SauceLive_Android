package com.mobidoo.saucelivesample

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.mobidoo.saucelive.SauceLiveCollection
import com.mobidoo.saucelive.SauceLiveCollectionType

class SauceCollectionViewActivity : Activity() {

    private lateinit var mContext: Context
    private lateinit var partnerId: String
    private var stageMode = false
    private var curationId = ""
    private lateinit var collectionTopBanner: SauceLiveCollection
    private lateinit var collectionBroadcast: SauceLiveCollection
    private lateinit var collectionSchedule: SauceLiveCollection
    private lateinit var collectionBroadcastTable: SauceLiveCollection
    private lateinit var collectionCuration: SauceLiveCollection
    private lateinit var collectionCurationDetail: SauceLiveCollection

    companion object {
        var onMoveTopBanner: Boolean = false
        var onMoveContentBanner: Boolean = false
        var onMoveBroadcast: Boolean = false
        var onMoveProduct: Boolean = false
        var onClickCuration: Boolean = false
        var onApplyForNotification: Boolean = false
        var onCancelForNotification: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sauce_collection_view)
        partnerId = intent.getStringExtra("partnerId") ?: ""
        stageMode = intent.getBooleanExtra("stageMode", false)
        curationId = intent.getStringExtra("curationId") ?: ""
        init()
    }

    private fun init() {
        mContext = this
        collectionTopBanner = findViewById(R.id.collection_top_banner)
        collectionBroadcast = findViewById(R.id.collection_broadcast)
        collectionSchedule = findViewById(R.id.collection_schedule)
        collectionBroadcastTable = findViewById(R.id.collection_broadcast_table)
        collectionCuration = findViewById(R.id.collection_curation)
        collectionCurationDetail = findViewById(R.id.collection_curation_detail)

        collectionTopBanner.setInit(partnerId, SauceLiveCollectionType.topBanner)
        collectionBroadcast.setInit(partnerId, SauceLiveCollectionType.broadcast)
        collectionSchedule.setInit(partnerId, SauceLiveCollectionType.schedule)
        collectionBroadcastTable.setInit(partnerId, SauceLiveCollectionType.broadcastTable)
        collectionCuration.setInit(partnerId, SauceLiveCollectionType.curation)
        collectionCurationDetail.setInit(partnerId, SauceLiveCollectionType.curationDetail, curationId)

        collectionTopBanner.setStageMode(stageMode)
        collectionBroadcast.setStageMode(stageMode)
        collectionSchedule.setStageMode(stageMode)
        collectionBroadcastTable.setStageMode(stageMode)
        collectionCuration.setStageMode(stageMode)
        collectionCurationDetail.setStageMode(stageMode)

        if (onMoveTopBanner) {
            collectionBroadcast.setOnMoveTopBanner { message -> Toast.makeText(mContext, "onMoveTopBanner", Toast.LENGTH_SHORT).show() }
        }

        if (onMoveContentBanner) {
            collectionBroadcast.setOnMoveContentBanner { message -> Toast.makeText(mContext, "onMoveContentBanner", Toast.LENGTH_SHORT).show() }
        }

        if (onMoveBroadcast) {
            collectionBroadcast.setOnMoveBroadcast { message -> Toast.makeText(mContext, "onMoveBroadcast", Toast.LENGTH_SHORT).show() }
            collectionSchedule.setOnMoveBroadcast { message -> Toast.makeText(mContext, "onMoveBroadcast", Toast.LENGTH_SHORT).show() }
            collectionBroadcastTable.setOnMoveBroadcast { message -> Toast.makeText(mContext, "onMoveBroadcast", Toast.LENGTH_SHORT).show() }
            collectionCurationDetail.setOnMoveBroadcast { message -> Toast.makeText(mContext, "onMoveBroadcast", Toast.LENGTH_SHORT).show() }
        }

        if (onMoveProduct) {
            collectionBroadcast.setOnMoveProduct { message -> Toast.makeText(mContext, "onMoveProduct", Toast.LENGTH_SHORT).show() }
            collectionBroadcastTable.setOnMoveProduct { message -> Toast.makeText(mContext, "onMoveProduct", Toast.LENGTH_SHORT).show() }
        }

        if (onClickCuration) {
            collectionCuration.setOnClickCuration { message -> Toast.makeText(mContext, "onClickCuration", Toast.LENGTH_SHORT).show() }
        }

        if (onApplyForNotification) {
            collectionTopBanner.setOnApplyForNotification { message -> Toast.makeText(mContext, "onApplyForNotification", Toast.LENGTH_SHORT).show() }
            collectionBroadcast.setOnApplyForNotification { message -> Toast.makeText(mContext, "onApplyForNotification", Toast.LENGTH_SHORT).show() }
            collectionSchedule.setOnApplyForNotification { message -> Toast.makeText(mContext, "onApplyForNotification", Toast.LENGTH_SHORT).show() }
            collectionBroadcastTable.setOnApplyForNotification { message -> Toast.makeText(mContext, "onApplyForNotification", Toast.LENGTH_SHORT).show() }
            collectionCuration.setOnApplyForNotification { message -> Toast.makeText(mContext, "onApplyForNotification", Toast.LENGTH_SHORT).show() }
            collectionCurationDetail.setOnApplyForNotification { message -> Toast.makeText(mContext, "onApplyForNotification", Toast.LENGTH_SHORT).show() }
        }

        if (onCancelForNotification) {
            collectionTopBanner.setOnCancelForNotification { message -> Toast.makeText(mContext, "onCancelForNotification", Toast.LENGTH_SHORT).show() }
            collectionBroadcast.setOnCancelForNotification { message -> Toast.makeText(mContext, "onCancelForNotification", Toast.LENGTH_SHORT).show() }
            collectionSchedule.setOnCancelForNotification { message -> Toast.makeText(mContext, "onCancelForNotification", Toast.LENGTH_SHORT).show() }
            collectionBroadcastTable.setOnCancelForNotification { message -> Toast.makeText(mContext, "onCancelForNotification", Toast.LENGTH_SHORT).show() }
            collectionCuration.setOnCancelForNotification { message -> Toast.makeText(mContext, "onCancelForNotification", Toast.LENGTH_SHORT).show() }
            collectionCurationDetail.setOnCancelForNotification { message -> Toast.makeText(mContext, "onCancelForNotification", Toast.LENGTH_SHORT).show() }
        }

        collectionTopBanner.load()
        collectionBroadcast.load()
        collectionSchedule.load()
        collectionBroadcastTable.load()
        collectionCuration.load()
        collectionCurationDetail.load()
    }
}