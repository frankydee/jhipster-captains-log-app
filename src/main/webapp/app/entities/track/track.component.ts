import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITrack } from 'app/shared/model/track.model';
import { Principal } from 'app/core';
import { TrackService } from './track.service';

@Component({
    selector: 'jhi-track',
    templateUrl: './track.component.html'
})
export class TrackComponent implements OnInit, OnDestroy {
    tracks: ITrack[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private trackService: TrackService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.trackService.query().subscribe(
            (res: HttpResponse<ITrack[]>) => {
                this.tracks = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTracks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITrack) {
        return item.id;
    }

    registerChangeInTracks() {
        this.eventSubscriber = this.eventManager.subscribe('trackListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
