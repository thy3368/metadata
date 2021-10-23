# Changelog

## 1.1.0
### Features
* [#30] - Introduce charts metadata
* [#37] - Enhance a view metadata fields
* [#38] - Introduce a query API with a proper filtering which will fetch an initial content
* [#41] - Implement an Oracle dialect
* [#42] - Implement a MSSQL dialect
* [#45] - Implement a MySQL dialect

### Improvements
* [#35] - Remove redundant cardinality field for a view metadata
* [#41] - Remove a chart metadata auto-configuration

### Bug fixes
* [#46] - Filtering functionality lacks order list parsing logic
* [#47] - Fix ordering logic
* [#48] - Generated query SQL is syntactically incorrect
* [#49] - Column names and aliases are always supposed to be uppercase

## 1.0.0
### Features
* View metadata
* Form metadata
* ComboBox metadata
* Lookup metadata