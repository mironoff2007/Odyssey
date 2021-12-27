package ru.alexgladkov.odyssey.compose.navigation

import ru.alexgladkov.odyssey.compose.AllowedDestination
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.Render
import ru.alexgladkov.odyssey.compose.ScreenType
import ru.alexgladkov.odyssey.compose.helpers.FlowBuilderModel

/**
 * Base builder, declarative helper for navigation graph builder
 * @see RootController
 * @property screenHost - canvas to draw UI
 */
class RootComposeBuilder {

    private val _screens: MutableList<AllowedDestination> = mutableListOf()
    private val _screenMap: HashMap<String, Render<Any?>> = hashMapOf()

    val screenMap: HashMap<String, Render<Any?>> = _screenMap

    fun addScreen(
        key: String,
        screenMap: Map<String, Render<Any?>>,
    ) {
        _screens.add(AllowedDestination(key = key, screenType = ScreenType.Simple))
        _screenMap.putAll(screenMap)
    }

    fun addFlow(
        key: String,
        flowBuilderModel: FlowBuilderModel
    ) {
        _screens.add(
            AllowedDestination(
                key = key,
                screenType = ScreenType.Flow(flowBuilderModel = flowBuilderModel)
            )
        )
    }

    fun build(): RootController = RootController().apply {
        updateScreenMap(_screenMap)
        setNavigationGraph(_screens)
    }
}