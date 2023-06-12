import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application


@Composable
fun inputFields(rubTextValue: MutableState<String>) {
    val rates = getRate()
    val ratesFrom = remember { mutableStateOf(getRate().first()) }
    val ratesTo = remember { mutableStateOf(getRate().first()) }
    if (rubTextValue.value == "") rubTextValue.value = "0"
    else if (rubTextValue.value.startsWith("0")) rubTextValue.value.drop(1)

    Column {
        Row(Modifier.border(2.dp, Color.Black).size(400.dp, 100.dp).align(alignment = Alignment.End)) {
            Column(Modifier.border(2.dp, Color.Black).size(100.dp, 100.dp)) {
                Spacer(Modifier.height(40.dp))
                Row {
                    Spacer(Modifier.width(25.dp))
                    createDropDownMenu(ratesFrom, mutableStateOf(false), rates)
                }
            }

            Row(Modifier.border(2.dp, Color.Black).size(300.dp, 100.dp)) {
                Spacer(Modifier.width(150.dp))
                Text(
                    if (rubTextValue.value.length > 1) {
                        rubTextValue.value.substring(1)
                    } else "0", Modifier.align(alignment = Alignment.CenterVertically)
                )
            }
        }
        Row(Modifier.border(2.dp, Color.Black).size(400.dp, 100.dp).align(alignment = Alignment.End)) {
            Column(Modifier.border(2.dp, Color.Black).size(100.dp, 100.dp)) {
                Spacer(Modifier.height(40.dp))
                Row {
                    Spacer(Modifier.width(25.dp))
                    createDropDownMenu(ratesTo, mutableStateOf(false), rates)
                }
            }
            Row(Modifier.border(2.dp, Color.Black).size(300.dp, 100.dp)) {
                Spacer(Modifier.width(150.dp))
                Text(
                    if (rubTextValue.value.length > 1) {
                        (rubTextValue.value.substring(1).toInt() * convert(ratesFrom.value, ratesTo.value)).toString()
                    } else "0", Modifier.align(alignment = Alignment.CenterVertically)
                )
            }
        }
    }

}

@Composable
fun createDropDownMenu(
    text: MutableState<String>, expanded: MutableState<Boolean> = mutableStateOf(false),
    data: List<String>
) {
    Box {
        Text(
            text.value, modifier = Modifier.fillMaxWidth(1f).clickable(onClick = { expanded.value = true })
        )
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
        ) {
            data.forEach {
                DropdownMenuItem({
                    text.value = it
                    expanded.value = false
                }) {
                    Text(it)
                }
            }

        }
    }
}

@Composable
fun mainScreen() {
    val rubTextValue = remember { mutableStateOf("") }
    Row(Modifier.border(1.dp, Color.Black).size(700.dp, 400.dp)) {
        keyBoard(rubTextValue = rubTextValue)
        inputFields(rubTextValue)
    }
}

@Composable
fun keyBoard(fontSize: Int = 75, cellSize: Int = 100, rubTextValue: MutableState<String>) {
    //var currentNumber by remember { mutableStateOf("") }

    var numberLabel by remember { mutableStateOf(0) }
    val eventList = List(10) {
        {
            rubTextValue.value += "$it"
            println(it)
        }
    }
    Column {

        for (i in 0..3) {
            Row {
                for (j in 0..2) {
                    Text(
                        "$numberLabel",
                        fontSize = fontSize.sp,
                        modifier = Modifier.clickable(
                            enabled = true, onClick = eventList[numberLabel]
                        ).border(2.dp, Color.Black).background(Color.LightGray)
                            .size(cellSize.dp).wrapContentSize(Alignment.Center)
                    )

                    numberLabel += 1
                    if (numberLabel == 10) {
                        Icon(
                            imageVector = Icons.Default.Delete, contentDescription = "",
                            Modifier.clickable { rubTextValue.value = "0" }.size(cellSize.dp)
                                .border(2.dp, Color.Black).background(Color.LightGray)
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowBack, contentDescription = "",
                            Modifier.clickable { rubTextValue.value = rubTextValue.value.dropLast(1) }.size(cellSize.dp)
                                .border(2.dp, Color.Black).background(Color.LightGray)
                        )
                        break
                    }
                }
            }
        }


    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        mainScreen()
    }
}