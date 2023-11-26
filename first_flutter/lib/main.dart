import 'package:flutter/material.dart';
import 'package:flutter_driver/driver_extension.dart';

void main() {
  enableFlutterDriverExtension();
  runApp(MyApp());
}

class _MyFormState extends State<MyForm> {
  TextEditingController textEditingController = TextEditingController();
  String displayedText = 'Hello, Flutter';

  @override
  Widget build(BuildContext context) {
    return Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(displayedText, key: Key('displayedText')),
            TextField(controller: textEditingController, key: Key('textField')),
            ElevatedButton(
                child: Text("Change Text!"),
                onPressed: () => setState(() {
                  displayedText = textEditingController.text;
                }),
                key: Key('changeTextButton')
            ),
          ],
        ));
  }
}

class MyForm extends StatefulWidget {
  @override
  _MyFormState createState() => _MyFormState();
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Button and TextField Test',
      home:
      Scaffold(appBar: AppBar(title: Text('AppBar Title')), body: MyForm()),
    );
  }
}
