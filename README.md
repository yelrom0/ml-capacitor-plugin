# ml-capacitor-plugin

Machine Learning Mobile Plugin for Capacitor using Pytorch and/or Tensorflow

Don't install this plugin yet. It's still in early development.

## Install

```bash
npm install ml-capacitor-plugin
npx cap sync
```

## API

<docgen-index>

- [`echo(...)`](#echo)
- [`loadImage()`](#loadimage)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

---

### loadImage()

```typescript
loadImage() => Promise<{ imageStr: string; }>
```

**Returns:** <code>Promise&lt;{ imageStr: string; }&gt;</code>

---

</docgen-api>
