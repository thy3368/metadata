# UI metadata provider

A UI metadata provider is a library to build a metadata driven UI.

## Description

This approach is especially useful in project teams with a high database competence rather than UI.

In general provides an element alignment by invocation of a single endpoint which provides all data required like
cardinality, language, font size, and font itself.

Main point is that library itself aims to provide a configurable metadata engine and a set of endpoints at the same time
UI should be written from scratch taking into account corresponding use case specifics to be able to properly handle a
metadata and construct itself based on it.

## Sample usage

Sample metadata configuration use case can be found in the
following [repository](https://github.com/sergeivisotsky/metadata-provider-demo)

## Extension points

The main metadata provisioning classes are provides Out-Of-The-Box like `FormMetadata`, `Layout` etc.

During the configuration a new class can be created and extend corresponding base class.

Example:

```java
public class ExtendedFormMetadata extends FormMetadata {

    private String facet;

    public String getFacet() {
        return facet;
    }

    public void setFacet(String facet) {
        this.facet = facet;
    }
}
```

An `ExtendedFormMetadata` extends a `FormMetadata` and provides an additional field `facet`.

At the same time all new fields should be added in a corresponding database table called `form_metadata` accordingly.

Sample can be found in the following `db.changelog-24-08-2021.xml` changelog file in the
following [repository](https://github.com/sergeivisotsky/metadata-provider-demo)

In addition to this a corresponding mapper should be created. An example mapper is a `FormMetadataMapper` for
a `FormMetadata`.

Each new mapper should implement `MetadataMapper<ResultSet, FormMetadata>` which obligatory should contain a `ResultSet`
as the first parameter and corresponding metadata class in the second.

`MetadataMapper` also provides a method `getSql()` which should contain a customized SQL.

_NOTE: An initial SQL should be always used from the
following [repository](https://github.com/sergeivisotsky/metadata-provider-demo) Also this is a repository from which
each back-end implementation should d got started_

## Usage on example of combo boxes

For a combobox style and a values a metadata is used as well. As an example:

```json
[
  {
    "id": 1,
    "codifier": "CD_001",
    "font": "Times New Roman",
    "fontSize": 12,
    "weight": 300,
    "height": 20,
    "displayable": true,
    "immutable": false,
    "comboContent": [
      {
        "key": "initial",
        "defaultValue": "Some initial value",
        "comboId": 1
      },
      {
        "key": "secondary",
        "defaultValue": "Some secondary value",
        "comboId": 1
      },
      {
        "key": "someThird",
        "defaultValue": "Some third value",
        "comboId": 1
      }
    ]
  }
]
```

In main section are contained a general properties of combo box like weight, height, Font and Font-size.

A `comboContent` sub-section contains a content of the combo box aka all possible default values.

In the result when UI invokes a metadata endpoint it first should construct the page itself and the second it should
parse an example combobox.

Sample in React:

```javascript
class SampleCombo extends Component {
    state = {
        metadata: null,
    }

    // process metadata
    componentDidMount() {
        const viewName = 'main';
        const self = this;
        axios.all([getMetadata(viewName), getMessageHeader(viewName)])
            .then(axios.spread((metadata, header) => {
                let formattedMetadata = formatMetadata(metadata);
                formattedMetadata = populateFields(header, formattedMetadata);
                self.setState({metadata: formattedMetadata, activeTab: formattedMetadata.sections.get('Consignor')});
            }));
    }

    // renders component
    render() {
        const {metadata, activeTab} = this.state;
        if (!metadata) return <Loader/>;
        const {
            codifier,
            font,
            fontSize,
            weight,
            height,
            displayable,
            immutable,
        } = metadata;
        return (
            <div id={uiName} className="klp-page">
               <select id="sample" name="sample" style="font={font};fontSize={fontSize};weight={weight};height={height}">
                  <option value="{key}">{defaultValue}</option>
               </select>
            </div>
        );
    }
}
```

_NOTE: This example is not an ideal however shows the main idea._

## Get started

To get started with a usage of metadata provider just add the following maven dependency to the main application:

```html

<dependency>
    <groupId>io.github.sergeivisotsky.metadata</groupId>
    <artifactId>metadata-selector</artifactId>
</dependency>
```

And the following dependency to a deployment application:

```html

<dependency>
    <groupId>io.github.sergeivisotsky.metadata</groupId>
    <artifactId>metadata-deploy</artifactId>
</dependency>
```

It is also highly recommended adding library starter to have a compatible version of both dependencies to
your `dependencyManegemnt` section of parent POM:

```html

<dependency>
    <groupId>io.github.sergeivisotsky.metadata</groupId>
    <artifactId>metadata-provider-bom</artifactId>
    <version>0.0.7</version>
    <scope>import</scope>
    <type>pom</type>
</dependency>
```

It will be loaded from Maven central.

Fully open-source and intuitive. Just enjoy!

## Contribution

There are just four rules of contribution:

1. Create an issue in GitHub with a fully described functionality
2. Fork repository, provide your enhancement and create a pull request
3. If a new class is required to be created it should contain a JavaDoc @author on top of the class
4. If a new class if a public API then JavaDoc should be written on top of each method. Method level JavaDoc should
   contain a description of what it does.