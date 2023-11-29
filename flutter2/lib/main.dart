import 'package:flutter/material.dart';
import 'package:flutter_driver/driver_extension.dart';

void main() {
  enableFlutterDriverExtension();
  runApp(MyApp());
}

class MyBody extends StatelessWidget {
  int item_count = 1;
  Widget _buildList(BuildContext context) {
    return ListView(
      children: [
        _tile(context, 'Grand Theater', '123 Main Street', Icons.theaters),
        _tile(context, 'Regal Cinema', '456 Elm Street', Icons.theaters),
        _tile(context, 'Majestic Movie House', '789 Oak Avenue', Icons.theaters),
        const Divider(),
        _tile(context, 'J\'s Kitchen', '757 Monterey Blvd', Icons.restaurant),
        _tile(context, 'La Ciccia hihi', '291 30th St', Icons.restaurant),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return _buildList(context);
  }

  Widget _tile(
    BuildContext context,
    String title,
    String subtitle,
    IconData icon,
  ) {
    final key = Key('list_item${item_count++}');
    return ListTile(
        key: key,
        title: Text(title,
            style: const TextStyle(fontWeight: FontWeight.w500, fontSize: 20)),
        subtitle: Text(subtitle),
        leading: Icon(icon, color: Colors.blue.shade500),
        onTap: () => Navigator.push(
              context,
              MaterialPageRoute(builder: (context) => SubPage(title, subtitle)), // 위젯으로 라우트 생성
            ));
  }
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'ListView Demo',
      home: Scaffold(
          appBar: AppBar(title: Text('ListView Demo')), body: MyBody()),
    );
  }
}

class SubPage extends StatelessWidget {
  String title;
  String subtitle;

  SubPage(this.title, this.subtitle);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(title: Text(title, key: Key('title'))),
        body: Text(subtitle));
  }
}
