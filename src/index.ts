import { registerPlugin } from '@capacitor/core';

import type { CapMLPlugin } from './definitions';

const CapML = registerPlugin<CapMLPlugin>('CapML', {
  web: () => import('./web').then(m => new m.CapMLWeb()),
});

export type {CapMLPlugin} from './definitions';
export { CapML };
