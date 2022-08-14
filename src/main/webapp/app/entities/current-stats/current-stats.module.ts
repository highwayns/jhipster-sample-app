import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CurrentStatsComponent } from './list/current-stats.component';
import { CurrentStatsDetailComponent } from './detail/current-stats-detail.component';
import { CurrentStatsUpdateComponent } from './update/current-stats-update.component';
import { CurrentStatsDeleteDialogComponent } from './delete/current-stats-delete-dialog.component';
import { CurrentStatsRoutingModule } from './route/current-stats-routing.module';

@NgModule({
  imports: [SharedModule, CurrentStatsRoutingModule],
  declarations: [CurrentStatsComponent, CurrentStatsDetailComponent, CurrentStatsUpdateComponent, CurrentStatsDeleteDialogComponent],
  entryComponents: [CurrentStatsDeleteDialogComponent],
})
export class CurrentStatsModule {}
