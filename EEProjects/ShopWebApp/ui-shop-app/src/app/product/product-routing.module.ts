import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProductItemResolver } from './product-item/product-item-resolver.service';
import { ProductItemComponent } from './product-item/product-item.component';
import { ProductListComponent } from './product-list/product-list.component';

const routes: Routes = [
  {
    path: 'all',
    component: ProductListComponent
  },
  {
    path: ':itemid',
    component: ProductItemComponent,
    resolve: {
      product: ProductItemResolver
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductRoutingModule {}