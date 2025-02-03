package com.vladislaviliev.newair.content.graph.state

import com.vladislaviliev.newair.readings.history.HistoryReading

data class GraphState(val readings: Collection<HistoryReading>, val message: String)