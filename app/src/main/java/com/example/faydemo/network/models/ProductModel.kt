package com.example.faydemo.network.models

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer

@Serializable
data class ProductResponse(
    val code: String,
    val product: Product? = null, // Product is nullable as it might be absent if not found
    val status: Int? = null, // Added status as Int, made nullable as it might not always be in the provided JSON snippet
    val status_verbose: String? = null // Added status_verbose as String, made nullable
)

@Serializable
data class Product(
    val knowledge_panels: Map<String, KnowledgePanel> // Keys are dynamic, so a Map is appropriate
)

@Serializable
data class KnowledgePanel(
    val elements: List<Element>,
    val level: String,
    val size: String? = null, // Can be null
    val title_element: TitleElement? = null, // Can be null
    val topics: List<String>,
    val evaluation: String? = null, // Can be null
    val expanded: Boolean? = null, // Can be null
    val type: String? = null, // Can be null
    val expand_for: String? = null // Can be null
)

@Serializable
data class Element(
    val element_type: String,
    // All possible element types are made nullable as only one will be present
    val text_element: TextElement? = null,
    val panel_element: PanelElement? = null,
    val action_element: ActionElement? = null,
    val panel_group_element: PanelGroupElement? = null,
    val table_element: TableElement? = null
)

@Serializable
data class TextElement(
    val html: String,
    val type: String? = null, // Can be null (e.g., "summary", "warning", "note", "default")
    val edit_field_id: String? = null, // Can be null
    val edit_field_type: String? = null, // Can be null
    val edit_field_value: String? = null, // Can be null
    val language: String? = null, // Can be null
    val lc: String? = null // Can be null
)

@Serializable
data class PanelElement(
    val panel_id: String
)

@Serializable
data class ActionElement(
    val actions: List<String>,
    val html: String
)

@Serializable
data class PanelGroupElement(
    val panel_ids: List<String>,
    val title: String,
    val panel_group_id: String? = null, // Can be null
    val image: Image? = null, // Can be null
    val type: String? = null // Can be null (e.g., "subcard")
)

@Serializable
data class Image(
    val alt: String,
    val id: String,
    val lc: String,
    val sizes: ImageSizes,
    val type: String
)

@Serializable
data class ImageSizes(
    @SerialName("100") val size100: ImageDimensions,
    @SerialName("200") val size200: ImageDimensions,
    @SerialName("400") val size400: ImageDimensions,
    val full: ImageDimensions
)

@Serializable
data class ImageDimensions(
    val h: Int,
    val url: String,
    val w: Int
)

@Serializable
data class TitleElement(
    val title: String,
    val grade: String? = null, // Can be null (e.g., "d", "c")
    val icon_url: String? = null, // Can be null
    val icon_color_from_evaluation: Boolean? = null, // Can be null
    val name: String? = null, // Can be null (e.g., "Green-Score")
    val subtitle: String? = null, // Can be null
    val type: String? = null, // Can be null (e.g., "grade")
    val icon_size: String? = null // Can be null (e.g., "small")
)

@Serializable
data class TableElement(
    val columns: List<Column>,
    val id: String,
    val rows: List<Row>,
    val title: String,
    val table_type: String? = null // Can be null (e.g., "percents")
)

@Serializable
data class Column(
    val text: String,
    val type: String
)

@Serializable
data class Row(
    val id: String? = null, // Can be null
    val values: List<Value>
)

@Serializable
data class Value(
    val icon_url: String? = null, // Can be null
    val text: String,
    val percent: Double? = null, // Can be null
    val evaluation: String? = null // Can be null (e.g., "neutral", "good", "bad", "average")
)